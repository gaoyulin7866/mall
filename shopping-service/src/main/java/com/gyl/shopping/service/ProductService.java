package com.gyl.shopping.service;

import com.gyl.shopping.dto.Product;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    public List<Product> getList(String orderBy, Integer categoryId, String keyword, Integer pageNum, Integer pageSize);

    Product getDetailById(Integer id);
}
