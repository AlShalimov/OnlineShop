package com.shalimov.dao;

import com.shalimov.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();

    void add(Product product);

    Product getById(long id);

    void edit(Product product);

    void deleteById(long id);

    Product getByName(String name);

    List<Product> search(String word);
}
