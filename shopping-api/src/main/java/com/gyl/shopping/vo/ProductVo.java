package com.gyl.shopping.vo;

import com.gyl.shopping.dto.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductVo implements Serializable {
    private List<Product> list = new ArrayList<>();
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}
