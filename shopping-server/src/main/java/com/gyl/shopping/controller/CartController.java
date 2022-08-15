package com.gyl.shopping.controller;

import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.filter.UserFilter;
import com.gyl.shopping.service.CartService;
import com.gyl.shopping.vo.CartVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/cart/list")
    public ResultResponse getCartList () {
        User user = UserFilter.currentUserId.get();
        List<CartVo> list = cartService.getList(user.getId());
        return ResultResponse.success().put(list);
    }

    @PostMapping("/cart/add")
    public ResultResponse addCart(@RequestParam("productId") Integer productId,
                                  @RequestParam("count") Integer count){
        if (productId == null || count == null) {
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUserId.get();
        cartService.addCart(productId, count, user.getId());
        return ResultResponse.success().put("添加成功！");
    }

    @PostMapping("/cart/update")
    public ResultResponse updataCart(@RequestParam("productId") Integer productId,
                                     @RequestParam("count") Integer count){
        if (productId == null || count == null) {
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUserId.get();
        cartService.updateCart(productId, count, user.getId());
        return ResultResponse.success().put("更新成功！");
    }

    @PostMapping("/cart/delete")
    public ResultResponse deleteCart(@RequestParam("productId") Integer productId){
        if (productId == null) {
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUserId.get();
        cartService.deleteCartByProductId(productId, user.getId());
        return ResultResponse.success().put("删除商品成功！");
    }

    @PostMapping("/cart/select")
    public ResultResponse cartProductSelect(@RequestParam("productId") Integer productId,
                                            @RequestParam("selected") Integer selected){

        User user = UserFilter.currentUserId.get();
        cartService.selectStatus(productId, selected, user.getId());
        return ResultResponse.success();
    }

    @PostMapping("/cart/selectAll")
    public ResultResponse cartProductSelectAll(@RequestParam("selected") Integer selected){
        User user = UserFilter.currentUserId.get();
        cartService.selectAllStatus(selected, user.getId());
        return ResultResponse.success();
    }
}
