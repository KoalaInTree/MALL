package com.mmall.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.*;
import com.mmall.pojo.*;
import com.mmall.service.IOrderService;
import com.mmall.util.BigDecimalUtil;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.FTPUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.OrderItemVo;
import com.mmall.vo.OrderProductVo;
import com.mmall.vo.OrderVo;
import com.mmall.vo.ShippingVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by CJ on 2017/11/27.
 */
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {

    private static Log log = LogFactory.getLog(OrderServiceImpl.class);
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse createOrder(Integer userId,Integer shippingId){
        List<Cart> cartList = cartMapper.selectCartCheckByUserId(userId);

        ServerResponse serverResponse = getOrderItem(cartList,userId);
        if ( !serverResponse.isSuccess() ){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        Order order = this.assembleOrder(userId,shippingId,payment);
        if (order == null){
            return ServerResponse.createByErrorMessage("订单生成错误");
        }
        if (CollectionUtils.isEmpty(orderItemList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        int rowCount = orderMapper.insert(order);
        if (rowCount == 0 ){
            return ServerResponse.createBySuccessMessage("订单创建失败");
        }
        //批量插入订单细节表
        orderItemMapper.batchInsert(orderItemList);
        //更新库存
        this.reduceProductStock(orderItemList);
        //清空购物车
        this.cleanCart(cartList);

        //装配用于显示订单的Vo
        OrderVo orderVo = this.assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    public ServerResponse cancel(Integer userId,Long orderNo){

        if (userId == null || orderNo == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Order order = orderMapper.selectByUserIdAndOderNo(userId,orderNo);

        if (order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        if (order.getStatus() > Const.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("订单已经付款不能取消");
        }
        order.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(order);
        if (rowCount == 0){
            return ServerResponse.createByErrorMessage("订单取消失败");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if (CollectionUtils.isNotEmpty(orderItemList)){
            for (OrderItem orderItem : orderItemList){
                Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
                product.setStock(product.getStock()+orderItem.getQuantity());
                productMapper.updateByPrimaryKeySelective(product);
            }
        }
        return ServerResponse.createBySuccess();
    }

    public ServerResponse getOrderDetail(Integer userId,Long orderNo){
        Order order = orderMapper.selectByUserIdAndOderNo(userId,orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectByUserIdAndOrderNo(userId,orderNo);
        //返回订单vo修改为线上imageHost,现在暂时使用happymmall的host
        // TODO: 2018/1/1
        OrderVo orderVo = this.assembleOrderVo2(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.listAllByUserId(userId);
        //返回订单vo修改为线上imageHost,现在暂时使用happymmall的host
        // TODO: 2018/1/1
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList,userId);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orderList,Integer userId){
        List<OrderVo> orderVoList = new ArrayList<OrderVo>();
        if (CollectionUtils.isNotEmpty(orderList)){
            if (userId == null){
                //// TODO: 2017/11/30
                for (Order order : orderList){
                    List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
                    //暂时使用assembleOrderVo2的imageHost
                    OrderVo orderVo = this.assembleOrderVo2(order,orderItemList);
                    orderVoList.add(orderVo);
                }
            }
            else {
                for (Order order : orderList){
                    List<OrderItem> orderItemList = orderItemMapper.selectByUserIdAndOrderNo(userId,order.getOrderNo());
                    //暂时使用assembleOrderVo2的imageHost
                    OrderVo orderVo = this.assembleOrderVo2(order,orderItemList);
                    orderVoList.add(orderVo);
                }
            }

        }
        return orderVoList;
    }

    public ServerResponse getCartOrderProduct(Integer userId){
        List<Cart> cartList = cartMapper.selectCartCheckByUserId(userId);
        if (CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByErrorMessage("没有选中的商品");
        }
        ServerResponse serverResponse = getOrderItem(cartList,userId);
        if ( !serverResponse.isSuccess() ){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        List<OrderItemVo> orderItemVoList = new ArrayList<OrderItemVo>();
        for (OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        OrderProductVo orderProductVo = new OrderProductVo();
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        return ServerResponse.createBySuccess(orderProductVo);
    }

    public ServerResponse pay(Long orderNo,Integer userId,String path){
        if (orderNo == null || userId== null || StringUtils.isBlank(path)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Order order = orderMapper.selectByUserIdAndOderNo(userId,orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        Map<String,String> resultMap = Maps.newHashMap();
        resultMap.put("orderNo",String.valueOf(orderNo));
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("mmall扫码支付,订单号:").append(orderNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("购买商品共").append(totalAmount).append("元").toString();
        //String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        List<OrderItem> orderItemList = orderItemMapper.selectByUserIdAndOrderNo(userId,orderNo);
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail

        for (OrderItem orderItem : orderItemList){
            GoodsDetail goods1 = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(), BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(),new Double(100)).longValue(), orderItem.getQuantity());
            goodsDetailList.add(goods1);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                File filer = new File(path);
                if (!filer.exists()){
                    filer.setWritable(true);
                    filer.mkdirs();
                }
                String filePath = String.format(path+"/qr-%s.png",
                        response.getOutTradeNo());
                String fileName = String.format("/qr-%s.png",
                        response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                File targetFile = new File(path,fileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    log.error("上传二维码异常:" + e);
                }
                log.info("filePath:" + filePath);
                //人为修改显示地址
                // TODO: 2017/12/29
                //String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName();
                String qrUrl = PropertiesUtil.getProperty("orderVo.img.prefix")+targetFile.getName();
                resultMap.put("qrUrl",qrUrl);
                return ServerResponse.createBySuccess(resultMap);

            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }


    public ServerResponse aliCallBack(Map<String,String> params){
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("非本商城订单");
        }

        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);
        payInfoMapper.insert(payInfo);
        return ServerResponse.createBySuccess();
    }

    public ServerResponse queryOrderPayStatus(Integer userId,Long orderNo){
        Order order = orderMapper.selectByUserIdAndOderNo(userId,orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }




    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (org.apache.commons.lang.StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    private OrderVo assembleOrderVo2(Order order,List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPostage(order.getPostage());
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        ShippingVo shippingVo = this.assembleShippingVo(shipping);
        orderVo.setShippingVo(shippingVo);
        orderVo.setReceiverName(shipping.getReceiverName());
        //----------------------需要修改
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        System.out.println(orderVo.getImageHost());
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        List<OrderItemVo> orderItemVoList = new ArrayList<OrderItemVo>();
        for (OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()));
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()));
        return orderVo;
    }

    private OrderVo assembleOrderVo(Order order,List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPostage(order.getPostage());
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        ShippingVo shippingVo = this.assembleShippingVo(shipping);
        orderVo.setShippingVo(shippingVo);
        orderVo.setReceiverName(shipping.getReceiverName());
        orderVo.setImageHost(PropertiesUtil.getProperty("orderVo.img.prefix"));
        System.out.println(orderVo.getImageHost());
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        List<OrderItemVo> orderItemVoList = new ArrayList<OrderItemVo>();
        for (OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()));
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()));
        return orderVo;
    }

    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        return orderItemVo;
    }

    private ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    private void reduceProductStock(List<OrderItem> orderItemList){

        for (OrderItem orderItem : orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    private void cleanCart(List<Cart> cartList){
        for (Cart cart : cartList){
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private Order assembleOrder(Integer userId,Integer shippingId,BigDecimal payment){
        Order order = new Order();
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setOrderNo(this.generateOrderNo());
        System.out.println(order.getOrderNo());
        return order;
    }

    private Long generateOrderNo(){
        Long currentTime = System.currentTimeMillis();
        String orderNo = currentTime+new Random().nextInt(10)+"";
        return Long.valueOf(orderNo);
    }

    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList){
        BigDecimal orderTotalPrice = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList){
            orderTotalPrice = BigDecimalUtil.add(orderTotalPrice.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return orderTotalPrice;
    }

    private ServerResponse getOrderItem(List<Cart> cartList , Integer userId){

        if (CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (Cart cart : cartList){
            Integer productId = cart.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()){
                return ServerResponse.createByErrorMessage("商品下架");
            }
            if (cart.getQuantity() > product.getStock()){
                return ServerResponse.createByErrorMessage("商品库存不足");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(userId);
            orderItem.setProductName(product.getName());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setProductId(productId);
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }




    //backend
    public ServerResponse manageOrderDetail(Long orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        OrderVo orderVo = this.assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    public ServerResponse manageOrderList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.listAll();
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList,null);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(orderVoList);
    }

    public ServerResponse<PageInfo> manageOrderSearch(Long orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        OrderVo orderVo = this.assembleOrderVo(order,orderItemList);
        PageInfo pageInfo = new PageInfo(Lists.newArrayList(order));
        pageInfo.setList(Lists.newArrayList(orderVo));
        return ServerResponse.createBySuccess(pageInfo);
    }


    public ServerResponse manageSendGoods(Long orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()){
            order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
            order.setSendTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
            return ServerResponse.createBySuccess("发货成功");
        }
        else if (order.getStatus() < Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createByErrorMessage("订单未付款或已取消");
        }else {
            return ServerResponse.createByErrorMessage("订单已经发货,不要重复发货");
        }

    }

}
