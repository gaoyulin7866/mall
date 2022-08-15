package com.gyl.shopping.vo;

import com.gyl.shopping.dto.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductVo {
    private List<Product> list = new ArrayList<>();
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}
