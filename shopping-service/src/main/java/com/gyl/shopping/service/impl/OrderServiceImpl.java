package com.gyl.shopping.service.impl;

import com.google.zxing.WriterException;
import com.gyl.shopping.common.*;
import com.gyl.shopping.dao.CartMapper;
import com.gyl.shopping.dao.OrderItemMapper;
import com.gyl.shopping.dao.OrderMapper;
import com.gyl.shopping.dao.ProductMapper;
import com.gyl.shopping.dto.Cart;
import com.gyl.shopping.dto.Order;
import com.gyl.shopping.dto.OrderItem;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.service.OrderService;
import com.gyl.shopping.vo.OrderItemVo;
import com.gyl.shopping.vo.OrderVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${qrcode.ip}")
    private String qrcodeAddr;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(String receiverName, Integer receiverMobile, String receiverAddress, Integer userId) {

        List<Cart> carts = cartMapper.selectAllSelected(userId);
        if (carts.size() == 0){
            throw new MallException(ExceptionEnum.EMPTY_CART);
        }

        int totalPrice = 0;
        String orderNo = OrderNoUtil.generateNo();
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : carts) {
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cart.getId());
            totalPrice += cart.getQuantity() * product.getPrice();
            orderItem.setCreateTime(new Date());
            orderItem.setProductId(product.getId());
            orderItem.setProductImg(product.getImage());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(product.getPrice() * cart.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setUpdateTime(new Date());
            orderItem.setOrderNo(orderNo);
            orderItems.add(orderItem);
        }

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverMobile(String.valueOf(receiverMobile));
        order.setReceiverName(receiverName);
        order.setOrderStatus(OrderStatus.ORDER_NOT_PAY.getCode());
        order.setPostage(10);
        order.setPaymentType(Constants.ONLINE_PAY);
        order.setPayTime(new Date());
        order.setCreateTime(new Date());
        orderMapper.insert(order);

        int i = orderItemMapper.batchInsert(orderItems);
        if (i < 0) {
            throw new MallException(ExceptionEnum.ORDER_ERROR);
        }

    }

    @Override
    public OrderVo detail(String orderNo, Integer userId) {
        Order order = orderMapper.selectByOrderNo(orderNo, userId);
        OrderVo orderVo = new OrderVo();
        orderVo.setCreateTime(order.getCreateTime());
        orderVo.setDeliveryTime(order.getDeliveryTime());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setOrderNo(orderNo);
        orderVo.setOrderStatus(order.getOrderStatus());
        orderVo.setOrderStatusName(OrderStatus.getStatusByCode(order.getOrderStatus()).getDesc());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPayTime(order.getPayTime());
        orderVo.setPostage(order.getPostage());
        orderVo.setReceiverAddress(order.getReceiverAddress());
        orderVo.setReceiverMobile(order.getReceiverMobile());
        orderVo.setReceiverName(order.getReceiverName());
        orderVo.setTotalPrice(order.getTotalPrice());
        orderVo.setUserId(order.getUserId());
        orderVo.setOrderItemList(getOrderItemVos(orderNo));
        return orderVo;
    }

    @Override
    public void cancelOrder(String orderNo, Integer userId) {
        Order order = orderMapper.selectByOrderNo(orderNo, userId);
        if(order == null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }

        order.setOrderStatus(OrderStatus.ORDER_CANCEL.getCode());

        int i = orderMapper.updateByPrimaryKey(order);
        if (i < 1){
            throw new MallException(ExceptionEnum.CANCEL_ORDER_ERROR);
        }
    }

    @Override
    public void finishOrder(String orderNo, Integer userId) {
        Order order = orderMapper.selectByOrderNo(orderNo, userId);
        if (order == null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }

        order.setOrderStatus(OrderStatus.ORDER_FINALLY.getCode());

        int i = orderMapper.updateByPrimaryKey(order);
        if(i < 1){
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }

    }

    @Override
    public void createQrcode(String orderNo) {
        if (StringUtils.isEmpty(orderNo)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }
        try {
//            String path = "https://" + qrcodeAddr + "/" + System.currentTimeMillis() + ".png";
            QrcodeUtil.createQrcode(orderNo, "/Users/gaoyulin/Desktop/img/download/" + System.currentTimeMillis() + ".png", QrcodeUtil.width, QrcodeUtil.height);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pay(String orderNo, Integer userId) {
        Order order = orderMapper.selectByOrderNo(orderNo, userId);
        if (order == null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }

        order.setPayTime(new Date());
        order.setOrderStatus(OrderStatus.ORDER_HAS_PAY.getCode());

        int i = orderMapper.updateByPrimaryKey(order);
        if (i < 1) {
            throw new MallException(ExceptionEnum.PAY_ERROR);
        }
    }

    @Override
    public List<OrderVo> list(Integer pageNum, Integer pageSize, Integer userId) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        Integer offset = (pageNum-1)*pageSize;
        List<Order> orders = orderMapper.selectByPage(offset, pageSize, userId);
        List<OrderVo> list = new ArrayList<>();
        for (Order order : orders) {
            OrderVo orderVo = new OrderVo();
            orderVo.setUserId(userId);
            orderVo.setTotalPrice(order.getTotalPrice());
            orderVo.setReceiverName(order.getReceiverName());
            orderVo.setReceiverMobile(order.getReceiverMobile());
            orderVo.setReceiverAddress(order.getReceiverAddress());
            orderVo.setPostage(order.getPostage());
            orderVo.setPayTime(order.getPayTime());
            orderVo.setPaymentType(order.getPaymentType());
            orderVo.setOrderStatusName(OrderStatus.getStatusByCode(order.getOrderStatus()).getDesc());
            orderVo.setOrderNo(order.getOrderNo());
            orderVo.setEndTime(order.getEndTime());
            orderVo.setDeliveryTime(order.getDeliveryTime());
            orderVo.setCreateTime(order.getCreateTime());
            orderVo.setOrderItemList(getOrderItemVos(order.getOrderNo()));
            list.add(orderVo);
        }
        return list;
    }

    public List<OrderItemVo> getOrderItemVos(String orderNo) {
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(orderNo);
        for (OrderItem orderItem : orderItems) {
            OrderItemVo orderItemVo = new OrderItemVo();
            orderItemVo.setOrderNo(orderNo);
            orderItemVo.setProductImg(orderItem.getProductImg());
            orderItemVo.setProductName(orderItem.getProductName());
            orderItemVo.setQuantity(orderItem.getQuantity());
            orderItemVo.setTotalPrice(orderItem.getTotalPrice());
            orderItemVo.setUnitPrice(orderItem.getUnitPrice());
            orderItemVoList.add(orderItemVo);
        }
        return orderItemVoList;
    }
}
