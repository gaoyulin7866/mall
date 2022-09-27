package com.gyl.shopping.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CartVo implements Serializable {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer quantity;

    private Integer selected;

    private Integer price;

    private Integer totalPrice;

    private String productName;

    private String productImage;

}