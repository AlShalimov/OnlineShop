package com.shalimov.onlineShop.service;

import com.shalimov.onlineShop.entity.Product;

import java.util.List;

public interface ProductService {
    public Product getById(long id);

    public List<Product> getAll();

    public void edit(Product product);

    public void insert(Product product);
}
