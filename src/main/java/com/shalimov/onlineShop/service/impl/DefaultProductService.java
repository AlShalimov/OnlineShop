package com.shalimov.onlineShop.service.impl;

import com.shalimov.onlineShop.dao.ProductDao;
import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {
    private ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    @Override
    public void addProduct(Product product) {
        productDao.add(product);
    }

    @Override
    public Product getProductById(long id) {
        return productDao.getById(id);
    }

    @Override
    public void editProduct(Product product) {
        productDao.edit(product);
    }

    @Override
    public void deleteProduct(long id) {
        productDao.deleteById(id);
    }


}
