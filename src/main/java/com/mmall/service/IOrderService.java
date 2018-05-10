package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * Created by CJ on 2017/11/27.
 */
public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallBack(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse cancel(Integer userId,Long orderNo);
    ServerResponse getCartOrderProduct(Integer userId);
    ServerResponse getOrderDetail(Integer userId,Long orderNo);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);


    //backend
    ServerResponse manageOrderDetail(Long orderNo);
    ServerResponse manageOrderList(int pageNum,int pageSize);
    ServerResponse<PageInfo> manageOrderSearch(Long orderNo,int pageNum,int pageSize);
    ServerResponse manageSendGoods(Long orderNo);

}
