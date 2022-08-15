package com.gyl.shopping.controller;

import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.filter.UserFilter;
import com.gyl.shopping.service.OrderService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PayController {

    @Resource
    private OrderService orderService;

    @GetMapping("/pay")
    public ResultResponse orderPay(@RequestParam("orderNo") String orderNo){
        if(StringUtils.isEmpty(orderNo)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }

        User user = UserFilter.currentUserId.get();
        orderService.pay(orderNo, user.getId());
        return ResultResponse.success();
    }
}
