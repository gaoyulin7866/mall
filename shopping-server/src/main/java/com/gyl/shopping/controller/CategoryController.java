package com.gyl.shopping.controller;

import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.Category;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.service.CategoryService;
import com.gyl.shopping.vo.CategoryTileVo;
import com.gyl.shopping.vo.CategoryVo;
import com.gyl.shopping.vo.ProductVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/category/list")
    public ResultResponse categoryList(){
        List<CategoryVo> list = categoryService.getList();
        return ResultResponse.success().put(list);
    }

    @PostMapping("/admin/category/add")
    public ResultResponse addCategoryByAdmin (@RequestParam("name") String name,
                                              @RequestParam("type") Integer type,
                                              @RequestParam("parentId") Integer parentId,
                                              @RequestParam("orderNum") Integer orderNum) {


        categoryService.addCategoryByAdmin(name, type, parentId, orderNum);
        return ResultResponse.success().put("添加成功！");
    }

    @PostMapping("/admin/category/update")
    public ResultResponse updateCategoryByAdmin(@RequestParam("id") Integer id,
                                                @RequestParam("name") String name,
                                                @RequestParam("type") Integer type,
                                                @RequestParam("parentId") Integer parentId,
                                                @RequestParam("orderNum") Integer orderNum){

        categoryService.updateCategroyByAdmin(id, name, type, parentId, orderNum);
        return ResultResponse.success().put("更新成功！");
    }

    @PostMapping("/admin/category/delete")
    public ResultResponse deleteCategoryByAdmin(@RequestParam("id") Integer id) {
        categoryService.deleteByAdmin(id);
        return ResultResponse.success().put("删除成功！");
    }

    @GetMapping("/admin/category/list")
    public ResultResponse CategoryList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize){
        CategoryTileVo categoryTileVo = categoryService.getListByTile(pageNum, pageSize);
        return ResultResponse.success().put(categoryTileVo);
    }

    @PostMapping("/admin/product/add")
    public ResultResponse addProductByAdmin(@RequestParam("name") String name,
                                            @RequestParam("categoryId") Integer categoryId,
                                            @RequestParam("price") Integer price,
                                            @RequestParam("stock") Integer stock,
                                            @RequestParam("detail") String detail,
                                            @RequestParam("image") String image){

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

        return ResultResponse.success().put("更新成功！");
    }

    @PostMapping("/admin/product/delete")
    public ResultResponse deleteByAdmin(@RequestParam("id") Integer id){
        return ResultResponse.success().put("删除成功！");
    }

    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ResultResponse batchUpdateSellStatus(@RequestParam("ids") String ids,
                                                @RequestParam("sellStatus") Integer sellStatus){

        return ResultResponse.success().put("更新成功！");

    }

    @GetMapping("/admin/product/list")
    public ResultResponse listByAdmin(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize){
        ProductVo productVo = new ProductVo();
        return ResultResponse.success().put(productVo);
    }
}
