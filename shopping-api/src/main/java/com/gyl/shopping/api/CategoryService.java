package com.gyl.shopping.api;

import com.gyl.shopping.vo.CategoryTileVo;
import com.gyl.shopping.vo.CategoryVo;

import java.util.List;

/**
 * @Author: gyl
 * @Description: 商品分类
 */
public interface CategoryService {
    public List<CategoryVo> getList();

    void addCategoryByAdmin(String name, Integer type, Integer parentId, Integer orderNum);

    void updateCategroyByAdmin(Integer id, String name, Integer type, Integer parentId, Integer orderNum);

    void deleteByAdmin(Integer id);

    CategoryTileVo getListByTile(Integer pageNum, Integer pageSize);
}
