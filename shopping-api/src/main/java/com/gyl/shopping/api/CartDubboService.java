package com.gyl.shopping.api;

import com.gyl.shopping.dto.Cart;
import com.gyl.shopping.dto.Product;

import java.util.List;

/**
 * @Author: gyl
 * @Date: 2022-11-07-上午10:23
 * @Description:
 */
public interface CartDubboService {
    public List<Cart> selectAllSelected(Integer userId);

    public Product selectByPrimaryKey(Integer id);
}
