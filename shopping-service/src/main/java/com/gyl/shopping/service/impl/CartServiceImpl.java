package com.gyl.shopping.service.impl;

import com.gyl.shopping.api.CartService;
import com.gyl.shopping.common.Constants;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.dao.CartMapper;
import com.gyl.shopping.dao.ProductMapper;
import com.gyl.shopping.dto.Cart;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.vo.CartVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: gyl
 * @Description: 购物车相关
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<CartVo> getList(Integer userId) {
        List<Cart> carts = cartMapper.selectAll(userId);
        List<CartVo> cartVos = new ArrayList<>();
        if (carts.size() > 0) {
            for (Cart cart : carts) {
                CartVo cartVo = new CartVo();
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product == null) {
                    continue;
                }
                cartVo.setId(cart.getId());
                cartVo.setPrice(product.getPrice());
                cartVo.setProductId(cart.getProductId());
                cartVo.setProductImage(product.getImage());
                cartVo.setProductName(product.getName());
                cartVo.setQuantity(cart.getQuantity());
                cartVo.setSelected(cart.getSelected());
                cartVo.setUserId(cart.getUserId());
                Integer totalPrice = product.getPrice() * cart.getQuantity();
                cartVo.setTotalPrice(totalPrice);
                cartVos.add(cartVo);
            }
        }
        return cartVos;
    }

    @Override
    public void addCart(Integer productId, Integer count, Integer userId) {
        Cart cart = cartMapper.selectCartByProductId(userId, productId);
        int i;
        if (cart == null){
            cart = new Cart();
            cart.setUpdateTime(new Date());
            cart.setQuantity(count);
            cart.setCreateTime(new Date());
            cart.setProductId(productId);
            cart.setSelected(Constants.HAS_SELECTED);
            cart.setUserId(userId);
            i = cartMapper.insert(cart);
        } else {
            cart.setQuantity(cart.getQuantity() + count);
            cart.setUpdateTime(new Date());
            i = cartMapper.updateByPrimaryKey(cart);
        }
        if (i < 1) {
            throw new MallException(ExceptionEnum.ADD_CART_ERROR);
        }
    }

    @Override
    public void updateCart(Integer productId, Integer count, Integer userId) {
        Cart cart = cartMapper.selectCartByProductId(productId, userId);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + count);
            int i = cartMapper.updateByPrimaryKey(cart);
            if (i < 1){
                throw new MallException(ExceptionEnum.UPDATE_ERROR);
            }
        } else {
            throw new MallException(ExceptionEnum.NO_CART_ERROR);
        }
    }

    @Override
    public void deleteCartByProductId(Integer productId, Integer userId) {
        int i = cartMapper.deleteByProductId(productId, userId);
        if (i < 1){
            throw new MallException(ExceptionEnum.DELETE_ERROR);
        }
    }

    @Override
    public void selectStatus(Integer productId, Integer selected, Integer userId) {
        Cart cart = cartMapper.selectCartByProductId(userId, productId);
        if (cart == null) {
            throw new MallException(ExceptionEnum.NOT_PRODUCT);
        }
        cart.setSelected(selected);
        int i = cartMapper.updateByPrimaryKey(cart);
        if (i < 1){
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void selectAllStatus(Integer selected, Integer userId) {
        int i = cartMapper.updateByPrimaryKeys(selected, userId);
        if (i < 1) {
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }
}
