package com.gyl.shopping.controller;

import com.gyl.shopping.api.CategoryService;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.vo.CategoryTileVo;
import com.gyl.shopping.vo.CategoryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
/**
 * @Author: gyl
 * @Description: 商品分类
 */
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
}
