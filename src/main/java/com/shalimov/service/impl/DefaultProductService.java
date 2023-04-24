package com.shalimov.service.impl;

import com.shalimov.dao.ProductDao;
import com.shalimov.entity.Product;
import com.shalimov.service.ProductService;

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

    @Override
    public List<Product> searchProducts(String word) {
        return productDao.search(word);
    }

}
