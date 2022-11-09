package com.gyl.shopping.service.impl;

import com.gyl.shopping.api.ProductService;
import com.gyl.shopping.common.Constants;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.dao.ProductMapper;
import com.gyl.shopping.dto.Product;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @Author: gyl
 * @Description: 商品相关
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ExecutorService executorService;

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

    @Override
    public void addProductByAdmin(String name, Integer categoryId, Integer price, Integer stock, String detail, String image) {
        if (StringUtils.isEmpty(name) ||
            StringUtils.isEmpty(categoryId)||
            StringUtils.isEmpty(price)||
            StringUtils.isEmpty(stock)||
            StringUtils.isEmpty(detail)||
            StringUtils.isEmpty(image)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }

        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setCreateTime(new Date());
        product.setDetail(detail);
        product.setImage(image);
        product.setName(name);
        product.setPrice(price);
        product.setStatus(Constants.ON_SHELF);
        product.setStock(stock);
        product.setUpdateTime(new Date());
        int insert = productMapper.insert(product);
        if (insert < 1) {
            throw new MallException(ExceptionEnum.ADD_PRODUCT_ERROR);
        }
    }

    @Override
    public void updateProductByAdmin(Integer id, String name, Integer categoryId, Integer price, Integer stock, String detail, String image) {
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            throw new MallException(ExceptionEnum.NOT_PRODUCT);
        }

        product.setUpdateTime(new Date());
        product.setStock(stock);
        product.setPrice(price);
        product.setName(name);
        product.setImage(image);
        product.setDetail(detail);
        product.setCategoryId(categoryId);
        int i = productMapper.updateByPrimaryKey(product);
        if (i < 1) {
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteByAdmin(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            throw new MallException(ExceptionEnum.NOT_PRODUCT);
        }
        int i = productMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            throw new MallException(ExceptionEnum.DELETE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchUpdateStatus(String ids, Integer sellStatus) {
        if(StringUtils.isEmpty(ids)){
            throw new MallException(ExceptionEnum.PARAMS_ERROR);
        }

        List<Integer> idList = Arrays.stream(ids.split(",")).mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
        int i = productMapper.batchUpdate(idList, sellStatus);
        if (i < 1) {
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchAddProductByAdmin(List<Product> productList, User user) {
        for (Product product : productList) {
            if(product.getCategoryId() == null){
                product.setCategoryId(1);
            }
            if(StringUtils.isEmpty(product.getImage())){
                product.setImage("暂无");
            }
            product.setCreateTime(new Date());
            product.setStatus(Constants.ON_DOWN);
            product.setUpdateTime(new Date());
            try {
                executorService.execute(new ProductTask(productMapper,product));
            } catch (Exception e) {
                e.printStackTrace();
                throw new MallException(ExceptionEnum.ADD_PRODUCT_ERROR);
            }
        }
    }

    @Override
    public ProductVo selectByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        Integer offset = (pageNum - 1) * pageSize;
        List<Product> list = productMapper.selectByPage(offset, pageSize);
        Integer total = productMapper.selectCount();
        ProductVo productVo = new ProductVo();
        productVo.setList(list);
        productVo.setPageNum(pageNum);
        productVo.setPageSize(pageSize);
        productVo.setTotal(total);
        return productVo;
    }

    public void updateStatus(Integer id, Integer status){
        Product product = productMapper.selectByPrimaryKey(id);
        if(product == null){
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }

        product.setStatus(status);
        product.setUpdateTime(new Date());
        int i = productMapper.updateByPrimaryKey(product);
        if (i < 1) {
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }
}

class ProductTask implements Runnable {
    private final Product product;

    private final ProductMapper productMapper;

    public ProductTask(ProductMapper productMapper, Product product){
        this.product = product;
        this.productMapper = productMapper;
    }

    @Override
    public void run() {
        int insert = productMapper.insert(product);
        if (insert < 1) {
            throw new MallException(ExceptionEnum.ADD_PRODUCT_ERROR);
        }
    }
}