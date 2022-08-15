package com.gyl.shopping.controller;

import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/product/list")
    public ResultResponse getList(@RequestParam(value = "orderBy", required = false) String orderBy,
                                 @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize){
        List<Product> list = productService.getList(orderBy, categoryId, keyword, pageNum, pageSize);
        return ResultResponse.success().put(list);
    }

    @GetMapping("/product/detail")
    public ResultResponse getDetail(@RequestParam("id") Integer id){
        if (id == null){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        Product product = productService.getDetailById(id);
        return ResultResponse.success().put(product);
    }
}
