package com.gyl.shopping.vo;

import com.gyl.shopping.dto.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryTileVo {
    private List<Category> list = new ArrayList<>();
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
}
