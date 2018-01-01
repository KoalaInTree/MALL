package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJ on 2017/11/21.
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ModelAndView detail(Integer productId){
        ServerResponse<ProductDetailVo> response = iProductService.getProductDetail(productId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("res",response);
        return new ModelAndView("portal/detail",map);
    }

    @RequestMapping("detail2.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail2(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list2.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword" ,required = false) String keyword,
                                         @RequestParam(value = "categoryId" ,required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum" ,defaultValue = "1")int pageNum,
                                         @RequestParam(value = "pageSize" ,defaultValue = "10")int pageSize,
                                         @RequestParam(value = "orderBy" ,required = false)String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ModelAndView list2(@RequestParam(value = "keyword" ,required = false) String keyword,
                                         @RequestParam(value = "categoryId" ,required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum" ,defaultValue = "1")int pageNum,
                                         @RequestParam(value = "pageSize" ,defaultValue = "10")int pageSize,
                                         @RequestParam(value = "orderBy" ,required = false)String orderBy){
        ServerResponse<PageInfo> response=iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
        Map map = new HashMap<String,Object>();
        map.put("res",response);
        return new ModelAndView("portal/list",map);
    }

    @RequestMapping("index.do")
    @ResponseBody
    public ModelAndView index(@RequestParam(value = "keyword" ,required = false) String keyword,
                              @RequestParam(value = "categoryId" ,required = false)Integer categoryId,
                              @RequestParam(value = "pageNum" ,defaultValue = "1")int pageNum,
                              @RequestParam(value = "pageSize" ,defaultValue = "10")int pageSize,
                              @RequestParam(value = "orderBy" ,required = false)String orderBy
                              ){
        return new ModelAndView("index",new HashMap<String, Object>());
    }


}
