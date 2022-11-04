package com.gyl.shopping.api;

import com.gyl.shopping.dto.Product;
import com.gyl.shopping.vo.ProductVo;

import java.util.List;

/**
 * @Author: gyl
 * @Description: 商品
 */
public interface ProductService {
    public List<Product> getList(String orderBy, Integer categoryId, String keyword, Integer pageNum, Integer pageSize);

    Product getDetailById(Integer id);

    void addProductByAdmin(String name, Integer categoryId, Integer price, Integer stock, String detail, String image);

    void updateProductByAdmin(Integer id, String name, Integer categoryId, Integer price, Integer stock, String detail, String image);

    void deleteByAdmin(Integer id);

    void batchUpdateStatus(String ids, Integer sellStatus);

    ProductVo selectByPage(Integer pageNum, Integer pageSize);
}
