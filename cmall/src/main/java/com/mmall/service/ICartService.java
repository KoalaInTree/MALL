package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

import java.util.List;

/**
 * Created by CJ on 2017/11/22.
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer productId, Integer userId, Integer count);
    ServerResponse<CartVo> update(Integer productId,Integer userId,Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);
    ServerResponse<CartVo> deleteProductByOne(Integer userId, Integer productId);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
