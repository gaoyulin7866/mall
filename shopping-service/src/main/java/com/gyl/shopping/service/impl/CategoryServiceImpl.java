package com.gyl.shopping.service.impl;

import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.dao.CategoryMapper;
import com.gyl.shopping.dto.Category;
import com.gyl.shopping.service.CategoryService;
import com.gyl.shopping.vo.CategoryTileVo;
import com.gyl.shopping.vo.CategoryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> getList() {
        List<Category> categories = categoryMapper.selectAll();
        List<CategoryVo> list = new ArrayList<>();
        list.addAll(sortCategory(categories, 0));
        return list;
    }

    @Override
    public void addCategoryByAdmin(String name, Integer type, Integer parentId, Integer orderNum) {
        Category category = categoryMapper.selectByName(name);
        if (category == null){
            category = new Category();
            category.setCreateTime(new Date());
            category.setName(name);
            category.setOrderNum(orderNum);
            category.setParentId(parentId);
            category.setType(type);
            category.setUpdateTime(new Date());
            int i = categoryMapper.insert(category);
            if (i < 1) {
                throw new MallException(ExceptionEnum.NEW_ERROR);
            }
        }else {
            throw new MallException(ExceptionEnum.CATEGORY_EXIST);
        }
    }

    @Override
    public void updateCategroyByAdmin(Integer id, String name, Integer type, Integer parentId, Integer orderNum) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new MallException(ExceptionEnum.CATEGORY_NOT_EXIST);
        }
        category.setName(name);
        category.setUpdateTime(new Date());
        category.setType(type);
        category.setParentId(parentId);
        category.setOrderNum(orderNum);
        int i = categoryMapper.updateByPrimaryKey(category);
        if (i < 1) {
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteByAdmin(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new MallException(ExceptionEnum.CATEGORY_NOT_EXIST);
        }
        int i = categoryMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            throw new MallException(ExceptionEnum.DELETE_ERROR);
        }
    }

    @Override
    public CategoryTileVo getListByTile(Integer pageNum, Integer pageSize) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 0) {
            pageSize = 15;
        }
        Integer offset = (pageNum - 1) * pageSize;
        List<Category> list = categoryMapper.selectByPage(offset, pageSize);
        Integer total = categoryMapper.selectCount();
        CategoryTileVo categoryTileVo = new CategoryTileVo();
        categoryTileVo.setList(list);
        categoryTileVo.setPageNum(pageNum);
        categoryTileVo.setPageSize(pageSize);
        categoryTileVo.setTotal(total);
        return null;
    }

    public List<CategoryVo> sortCategory(List<Category> categories, Integer parentId){
        List<CategoryVo> list = new ArrayList<>();
        if (categories.size() != 0) {
            List<Category> collect = categories.stream().filter(v -> Objects.equals(v.getParentId(), parentId)).collect(Collectors.toList());
            List<Category> nextCollect = categories.stream().filter(item -> !Objects.equals(item.getParentId(), parentId)).collect(Collectors.toList());
            collect.forEach(v -> {
                CategoryVo categoryVo = new CategoryVo();
                List<CategoryVo> nextList = new ArrayList<>();
                nextList.addAll(sortCategory(nextCollect, v.getId()));
                categoryVo.setId(v.getId());
                categoryVo.setName(v.getName());
                categoryVo.setOrderNum(v.getOrderNum());
                categoryVo.setParentId(v.getParentId());
                categoryVo.setType(v.getType());
                categoryVo.setChildCategory(nextList);
                list.add(categoryVo);
            });
        }
        return list;
    }
}
