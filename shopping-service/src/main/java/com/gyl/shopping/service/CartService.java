package com.gyl.shopping.service;

import com.gyl.shopping.vo.CartVo;

import java.util.List;

public interface CartService {
    List<CartVo> getList(Integer userId);

    void addCart(Integer productId, Integer count, Integer userId);

    void updateCart(Integer productId, Integer count, Integer userId);

    void deleteCartByProductId(Integer productId, Integer userId);

    void selectStatus(Integer productId, Integer selected, Integer userId);

    void selectAllStatus(Integer selected, Integer userId);
}
