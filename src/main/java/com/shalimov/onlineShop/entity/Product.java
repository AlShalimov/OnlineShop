package com.shalimov.onlineShop.entity;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private long id;
    private String name;
    private double price;
    private String description;
    private String imagePath;
}