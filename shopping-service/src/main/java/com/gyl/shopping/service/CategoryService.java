package com.gyl.shopping.service;

import com.gyl.shopping.vo.CategoryTileVo;
import com.gyl.shopping.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    public List<CategoryVo> getList();

    void addCategoryByAdmin(String name, Integer type, Integer parentId, Integer orderNum);

    void updateCategroyByAdmin(Integer id, String name, Integer type, Integer parentId, Integer orderNum);

    void deleteByAdmin(Integer id);

    CategoryTileVo getListByTile(Integer pageNum, Integer pageSize);
}
