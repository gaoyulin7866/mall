package com.gyl.shopping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Cart implements Serializable {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer quantity;

    private Integer selected;

    private Date createTime;

    private Date updateTime;

}