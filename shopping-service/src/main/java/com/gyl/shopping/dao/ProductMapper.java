package com.gyl.shopping.dao;

import com.gyl.shopping.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getList(@Param("orderBy") String orderBy,
                          @Param("categoryId") Integer categoryId,
                          @Param("keyword") String keyword,
                          @Param("offset") Integer offset,
                          @Param("pageSize") Integer pageSize);
}