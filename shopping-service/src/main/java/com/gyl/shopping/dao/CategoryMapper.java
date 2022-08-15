package com.gyl.shopping.dao;

import com.gyl.shopping.dto.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAll();

    Category selectByName(@Param("name") String name);

    Integer selectCount();

    List<Category> selectByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}