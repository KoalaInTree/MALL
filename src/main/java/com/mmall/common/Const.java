package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by CJ on 2017/11/16.
 */
public class Const {
    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String USERNAME= "USERNAME";
    public static final String EMAIL = "EMAIL";
    public static final String RESULT = "RESULT";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc","price_desc");
    }
    /**
     * Created by CJ on 2017/12/27.
     */
    public enum ResultStatusEnum {
        Cart_addSuccess(0,"您的商品已成功加入购物车"),
        Cart_addError(1,"您的商品添加失败请重试");
        private String value;
        private int code;
        ResultStatusEnum(int code,String value){
            this.code=code;
            this.value=value;
        }
        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
    public interface ROLE{
        int ROLE_ADMIN = 1;//管理员
        int ROLE_CUSTOMER = 0;//顾客
    }

    public interface Cart{
        int CHECKED = 1;//购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
    public enum ProductStatusEnum {
        ON_SALE(1,"在线");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code=code;
            this.value=value;
        }
        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭")
        ;
        private int code;
        private String value;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        public static String codeOf(int code){
            for (OrderStatusEnum typeEnum : values()){
                if (typeEnum.getCode() == code) {
                    return typeEnum.getValue();
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }

    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝")
        ;
        private int code;
        private String value;

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }

    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付")
        ;
        private int code;
        private String value;

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }
        public static String codeOf(int code){
            for (PaymentTypeEnum typeEnum : values()){
                if (typeEnum.getCode() == code) {
                    return typeEnum.getValue();
                }
            }
            return null;
        }
        public String getValue() {
            return value;
        }
    }

}
