package com.shalimov.onlineShop.service;

import com.shalimov.onlineShop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    void addProduct(Product product);

    Product getProductById(long id);

    void editProduct(Product product);

    void deleteProduct(long id);
}
