package com.shalimov.onlineShop.dao;

import com.shalimov.onlineShop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();

    Product getById(long id);

    void edit(Product product);

    void insert(Product product);
}
