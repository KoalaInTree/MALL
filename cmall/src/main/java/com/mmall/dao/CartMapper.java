package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateCartQuantityByUserIdAndProductId(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("quantity")Integer quantity);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByProductIdAndUserId(@Param("userId")Integer userId,@Param("productId") Integer productId);

    List<Cart> selectCartByUserId(Integer userId);

    List<Cart> selectCartCheckByUserId(Integer userId);

    int selectCartUnCheckByUserId(Integer userId);

    int deleteByProductIdList(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    int deleteByProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int updateSelectOrUnSelect(@Param("userId") Integer userId,@Param("productId") Integer productId,@Param("checked") Integer checked);

    int selectCartProductCount(Integer userId);
}