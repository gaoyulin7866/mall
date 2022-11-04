package com.gyl.shopping.controller;

import com.gyl.order.api.OrderDubboService;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.filter.UserFilter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @Reference(version = "${demo.service.version}", group = "${demo.service.group}")
    private OrderDubboService orderDubboService;

    @GetMapping("/pay")
    public ResultResponse orderPay(@RequestParam("orderNo") String orderNo){
        if(StringUtils.isEmpty(orderNo)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }

        User user = UserFilter.currentUser.get();
        orderDubboService.pay(orderNo, user.getId());
        return ResultResponse.success();
    }
}
