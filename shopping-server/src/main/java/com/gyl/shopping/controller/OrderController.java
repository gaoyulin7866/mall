package com.gyl.shopping.controller;

import com.gyl.order.api.OrderDubboService;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.ResultResponse;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.filter.UserFilter;
import com.gyl.shopping.vo.OrderVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Author: gyl
 * @Description: 订单
 */
@RestController
public class OrderController {

    @Reference(version = "${demo.service.version}", group = "${demo.service.group}")
    private OrderDubboService orderServiceDubbo;

    @PostMapping("/order/create")
    public ResultResponse createOrder(@RequestParam("receiverName") String receiverName,
                                      @RequestParam("receiverMobile") String receiverMobile,
                                      @RequestParam("receiverAddress") String receiverAddress){

        User user = UserFilter.currentUser.get();
        orderServiceDubbo.create(receiverName,receiverMobile,receiverAddress, user.getId());
        return ResultResponse.success().put("订单创建成功！");
    }

    @GetMapping("/order/detail")
    public ResultResponse detail(@RequestParam("orderNo") String orderNo){

        User user = UserFilter.currentUser.get();
        OrderVo orderVo = orderServiceDubbo.detail(orderNo, user.getId());
        return ResultResponse.success().put(orderVo);
    }

    @GetMapping("/order/list")
    public ResultResponse list(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize){

        User user = UserFilter.currentUser.get();
        List<OrderVo> list = orderServiceDubbo.list(pageNum, pageSize, user.getId());
        return ResultResponse.success().put(list);
    }

    @PostMapping("/order/cancel")
    public ResultResponse cancelOrder(@RequestParam("orderNo") String orderNo){
        if (StringUtils.isEmpty(orderNo)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        User user = UserFilter.currentUser.get();
        orderServiceDubbo.cancelOrder(orderNo, user.getId());
        return ResultResponse.success().put("取消成功!");
    }

    @PostMapping("/order/qrcode")
    public ResultResponse orderQr(@RequestParam("orderNo") String orderNo){
        orderServiceDubbo.createQrcode(orderNo);
        return ResultResponse.success();
    }

    @PostMapping("/order/finish")
    public ResultResponse orderFinish(@RequestParam("orderNo") String orderNo){
        if(StringUtils.isEmpty(orderNo)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }

        User user = UserFilter.currentUser.get();
        orderServiceDubbo.finishOrder(orderNo, user.getId());
        return ResultResponse.success().put("订单完成！");
    }

    @GetMapping("/admin/order/list")
    public ResultResponse getList(@RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize){
        List<OrderVo> list = orderServiceDubbo.listByAdmin(pageNum, pageSize);
        return ResultResponse.success().put(list);
    }

    @PostMapping("/admin/order/delivered")
    public ResultResponse delivered(@RequestParam("orderNo") String orderNo){
        orderServiceDubbo.delivered(orderNo);
        return ResultResponse.success().put("发货成功！");
    }

}
