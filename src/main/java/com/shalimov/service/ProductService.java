package com.shalimov.service;

import com.shalimov.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    void addProduct(Product product);

    Product getProductById(long id);

    void editProduct(Product product);

    void deleteProduct(long id);

    List<Product> searchProducts(String word);
}
