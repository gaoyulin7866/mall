package com.gyl.shopping.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrderItemVo implements Serializable {
    private String orderNo;

    private String productName;

    private String productImg;

    private Integer unitPrice;

    private Integer quantity;

    private Integer totalPrice;


}