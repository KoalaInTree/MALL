package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by CJ on 2017/11/18.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String name,String parentId){
        if (StringUtils.isBlank(name) || StringUtils.isBlank(parentId)){
            return ServerResponse.createByErrorMessage("参数异常");
        }
        Category category = new Category();
        category.setName(name);
        category.setParentId(Integer.valueOf(parentId));
        category.setStatus(true);
        int resultCount = categoryMapper.insert(category);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategory(String categoryName,String categoryId){
        if (StringUtils.isBlank(categoryName) || (StringUtils.isBlank(categoryId))){
            return ServerResponse.createByErrorMessage("参数异常");
        }
        Category category = new Category();
        category.setId(Integer.valueOf(categoryId));
        category.setName(categoryName);
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新品类成功");
        }
        return ServerResponse.createByErrorMessage("更新品类失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(String categoryId){
        if (StringUtils.isBlank(categoryId)){
            return ServerResponse.createByErrorMessage("参数异常");
        }
        List<Category> categoryList = categoryMapper.getChildrenParallelCategoryByPrimaryKey(Integer.valueOf(categoryId));
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("没有当前品类id的子节点");
            return ServerResponse.createByErrorMessage("没有当前品类id的子节点");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        if (categoryId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryList = Lists.newArrayList();
        for (Category categoryItem : categorySet){
            categoryList.add(categoryItem.getId());
        }
        return ServerResponse.createBySuccess(categoryList);

    }

    public Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if ( category != null ){
            categorySet.add(category);
        }

        List<Category> categoryList = categoryMapper.getChildrenParallelCategoryByPrimaryKey(categoryId);
        for (Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }
        System.out.println(1);
        return categorySet;
    }


}
