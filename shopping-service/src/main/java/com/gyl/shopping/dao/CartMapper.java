package com.gyl.shopping.dao;

import com.gyl.shopping.dto.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProductId(@Param("productId") Integer productId,@Param("userId") Integer userId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    int updateByPrimaryKeys(@Param("selected") Integer selected, @Param("userId") Integer userId);

    List<Cart> selectAll(@Param("userId") Integer userId);
    List<Cart> selectAllSelected(@Param("userId") Integer userId);

    Cart selectCartByProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
}