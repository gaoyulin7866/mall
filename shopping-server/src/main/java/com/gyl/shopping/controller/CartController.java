package com.gyl.shopping.controller;

import com.gyl.shopping.api.CartService;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.filter.UserFilter;
import com.gyl.shopping.vo.CartVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
/**
 * @Author: gyl
 * @Description: 购物车
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/list")
    public ResultResponse getCartList () {
        User user = UserFilter.currentUser.get();
        List<CartVo> list = cartService.getList(user.getId());
        return ResultResponse.success().put(list);
    }

    @PostMapping("/add")
    public ResultResponse addCart(@RequestParam("productId") Integer productId,
                                  @RequestParam("count") Integer count){
        if (productId == null || count == null) {
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUser.get();
        cartService.addCart(productId, count, user.getId());
        return ResultResponse.success().put("添加成功！");
    }

    @PostMapping("/update")
    public ResultResponse updataCart(@RequestParam("productId") Integer productId,
                                     @RequestParam("count") Integer count){
        if (productId == null || count == null) {
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUser.get();
        cartService.updateCart(productId, count, user.getId());
        return ResultResponse.success().put("更新成功！");
    }

    @PostMapping("/delete")
    public ResultResponse deleteCart(@RequestParam("productId") Integer productId){
        if (productId == null) {
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUser.get();
        cartService.deleteCartByProductId(productId, user.getId());
        return ResultResponse.success().put("删除商品成功！");
    }

    @PostMapping("/select")
    public ResultResponse cartProductSelect(@RequestParam("productId") Integer productId,
                                            @RequestParam("selected") Integer selected){

        User user = UserFilter.currentUser.get();
        cartService.selectStatus(productId, selected, user.getId());
        return ResultResponse.success();
    }

    @PostMapping("/selectAll")
    public ResultResponse cartProductSelectAll(@RequestParam("selected") Integer selected){
        User user = UserFilter.currentUser.get();
        cartService.selectAllStatus(selected, user.getId());
        return ResultResponse.success();
    }
}
