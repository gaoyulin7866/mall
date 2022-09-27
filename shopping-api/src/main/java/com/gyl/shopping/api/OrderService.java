package com.gyl.shopping.api;

import com.gyl.shopping.vo.OrderVo;

import java.util.List;

public interface OrderService {
    void create(String receiverName, Integer receiverMobile, String receiverAddress, Integer userId);

    List<OrderVo> list(Integer pageNum, Integer pageSize, Integer userId);

    OrderVo detail(String orderNo, Integer userId);

    void cancelOrder(String orderNo, Integer userId);

    void finishOrder(String orderNo, Integer userId);

    void createQrcode(String orderNo);

    void pay(String orderNo, Integer userId);

    List<OrderVo> listByAdmin(Integer pageNum, Integer pageSize);

    void delivered(String orderNo);
}
