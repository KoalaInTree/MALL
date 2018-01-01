package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by CJ on 2017/11/22.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse<Map> save(Integer userId, Shipping shipping){
        if (shipping == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        shipping.setUserId(userId);
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount > 0){
            Map resultMap = Maps.newHashMap();
            resultMap.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("收货地址添加成功",resultMap);
        }
        return ServerResponse.createByErrorMessage("收货地址添加失败");
    }

    public ServerResponse<String> update(Integer userId, Shipping shipping){
        if (shipping == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        shipping.setUserId(userId);
        int resultCount = shippingMapper.updateByPrimaryKeyAndUserIdSelective(shipping);
        if (resultCount > 0){
            return ServerResponse.createBySuccess("收货地址更新成功");
        }
        return ServerResponse.createByErrorMessage("收货地址更新失败");
    }

    public ServerResponse<String> delete(Integer userId,Integer shippingId){
        if (shippingId == null || userId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int resultCount = shippingMapper.deleteByPrimaryKeyAndUserId(shippingId,userId);
        if (resultCount > 0){
            return ServerResponse.createBySuccess("收货地址删除成功");
        }
        return ServerResponse.createByErrorMessage("收货地址删除失败");
    }

    public ServerResponse<Shipping> select(Integer userId,Integer shippingId){
        if (shippingId == null || userId ==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKeyAndUserId(shippingId,userId);
        if (shipping != null){
            return ServerResponse.createBySuccess("收货地址查询成功",shipping);
        }
        return ServerResponse.createByErrorMessage("收货地址查询失败");
    }

    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize){
        if (userId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.listByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
