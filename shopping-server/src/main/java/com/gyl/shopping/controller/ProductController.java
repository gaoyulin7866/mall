package com.gyl.shopping.controller;

import com.gyl.shopping.api.ProductService;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.vo.ProductVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @Author: gyl
 * @Description: 商品
 */
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

    @PostMapping("/admin/product/add")
    public ResultResponse addProductByAdmin(@RequestParam("name") String name,
                                            @RequestParam("categoryId") Integer categoryId,
                                            @RequestParam("price") Integer price,
                                            @RequestParam("stock") Integer stock,
                                            @RequestParam("detail") String detail,
                                            @RequestParam("image") String image){

        productService.addProductByAdmin(name, categoryId, price, stock, detail, image);

        return ResultResponse.success().put("新增成功！");

    }

    @PostMapping("/admin/upload/file")
    public ResultResponse upload(@RequestParam("file") File file){
        return ResultResponse.success().put("上传成功！");
    }

    @PostMapping("/admin/product/update")
    public ResultResponse updateProductByAdmin(@RequestParam("id") Integer id,
                                               @RequestParam("name") String name,
                                               @RequestParam("categoryId") Integer categoryId,
                                               @RequestParam("price") Integer price,
                                               @RequestParam("stock") Integer stock,
                                               @RequestParam("detail") String detail,
                                               @RequestParam("image") String image){

        productService.updateProductByAdmin(id, name, categoryId, price, stock, detail, image);

        return ResultResponse.success().put("更新成功！");
    }

    @PostMapping("/admin/product/delete")
    public ResultResponse deleteByAdmin(@RequestParam("id") Integer id){
        productService.deleteByAdmin(id);
        return ResultResponse.success().put("删除成功！");
    }

    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ResultResponse batchUpdateSellStatus(@RequestParam("ids") String ids,
                                                @RequestParam("sellStatus") Integer sellStatus){

        productService.batchUpdateStatus(ids, sellStatus);
        return ResultResponse.success().put("更新成功！");

    }

    @GetMapping("/admin/product/list")
    public ResultResponse listByAdmin(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize){
        ProductVo productVo = productService.selectByPage(pageNum, pageSize);
        return ResultResponse.success().put(productVo);
    }
}
