package com.gyl.shopping.service.impl;

import com.gyl.shopping.api.CartDubboService;
import com.gyl.shopping.dao.CartMapper;
import com.gyl.shopping.dao.ProductMapper;
import com.gyl.shopping.dto.Cart;
import com.gyl.shopping.dto.Product;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: gyl
 * @Date: 2022-11-07-上午10:15
 * @Description:
 */
@Service(version = "${demo.service.version}", group = "${demo.service.group}")
public class CartDubboServiceImpl implements CartDubboService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    public List<Cart> selectAllSelected(Integer userId) {
        return cartMapper.selectAllSelected(userId);
    }

    public Product selectByPrimaryKey(Integer id){
        return productMapper.selectByPrimaryKey(id);
    }
}
