package com.gyl.shopping.service.impl;

import com.gyl.shopping.common.Constants;
import com.gyl.shopping.dao.ProductMapper;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> getList(String orderBy, Integer categoryId, String keyword, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(pageNum) || pageNum < 1) {
            pageNum = 1;
        }
        if (StringUtils.isEmpty(pageSize) || pageSize < 1) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(orderBy) || (!orderBy.equals(Constants.ORDER_BY_DESC) && !orderBy.equals(Constants.ORDER_BY_ASC))) {
            orderBy = Constants.ORDER_BY_DESC;
        }
        Integer offset = (pageNum - 1) * pageSize;
        return productMapper.getList(orderBy, categoryId, keyword, offset, pageSize);
    }

    @Override
    public Product getDetailById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
