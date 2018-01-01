package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * Created by CJ on 2017/11/22.
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    public ServerResponse<CartVo> add(Integer productId,Integer userId,Integer count){
        if (productId == null || count <0 || count ==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Cart cart =cartMapper.selectCartByProductIdAndUserId(userId,productId);
        if (cart == null){
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cart);
        }
        else {
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    public ServerResponse<CartVo> update(Integer productId,Integer userId,Integer count){
        if (productId == null || count <0 || count ==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        cartMapper.updateCartQuantityByUserIdAndProductId(userId,productId,count);
        return list(userId);
    }

    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds){
        if (StringUtils.isBlank(productIds)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        cartMapper.deleteByProductIdList(userId,productIdList);
        return list(userId);
    }

    public ServerResponse<CartVo> deleteProductByOne(Integer userId, Integer productId){
        if (productId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        cartMapper.deleteByProductId(userId,productId);
        return list(userId);
    }

    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }


    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId, Integer checked){
        if (userId == null || checked == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        cartMapper.updateSelectOrUnSelect(userId,productId,checked);
        return list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if ( userId == null ){
            return ServerResponse.createBySuccess(0);
        }
        int cartProductCount = cartMapper.selectCartProductCount(userId);
        return ServerResponse.createBySuccess(cartProductCount);
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)){
            for (Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductChecked(cartItem.getChecked());
                if (cartItem.getProductId() != null){
                    Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                    if (product != null){
                        cartProductVo.setProductMainImage(product.getMainImage());
                        cartProductVo.setProductId(product.getId());
                        cartProductVo.setProductName(product.getName());
                        cartProductVo.setProductSubtitle(product.getSubtitle());
                        cartProductVo.setProductStock(product.getStock());
                        cartProductVo.setProductPrice(product.getPrice());
                        int buyLimitCount = 0;
                        if (cartItem.getQuantity() > product.getStock()){
                            buyLimitCount = product.getStock();
                            Cart cartForQuantity = new Cart();
                            cartForQuantity.setId(cartItem.getId());
                            cartForQuantity.setQuantity(buyLimitCount);
                            cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        }else {
                            buyLimitCount = cartItem.getQuantity();
                            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                        }
                        cartProductVo.setQuantity(buyLimitCount);
                        //计算总价
                        cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity().doubleValue()));
                        cartProductVo.setProductStatus(product.getStatus());
                    }
                    if (cartItem.getChecked() == Const.Cart.CHECKED){
                        cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                    }
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    private boolean getAllCheckStatus(Integer userId){
        if (userId == null){
            return false;
        }
        return cartMapper.selectCartUnCheckByUserId(userId)==0;
    }

}
