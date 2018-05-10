package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CJ on 2017/11/18.
 */
public interface ICategoryService {
    ServerResponse addCategory(String name, String parentId);
    ServerResponse updateCategory(String categoryName,String categoryId);
    ServerResponse<List<Category>> getChildrenParallelCategory(String categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

}
