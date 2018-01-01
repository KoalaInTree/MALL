package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByPrimaryKeyAndUserId(@Param("id") Integer id, @Param("userId")Integer userId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    Shipping selectByPrimaryKeyAndUserId(@Param("id")Integer id, @Param("userId")Integer userId);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKeyAndUserIdSelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> listByUserId(Integer userId);
}