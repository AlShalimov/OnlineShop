package com.shalimov.onlineShop.dao;

import com.shalimov.onlineShop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();

    void add(Product product);

    Product getById(long id);

    void edit(Product product);

    void deleteById(long id);

    Product getByName(String name);
}
