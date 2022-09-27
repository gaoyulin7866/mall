package com.gyl.shopping.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryVo implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    private List<CategoryVo> childCategory;

}