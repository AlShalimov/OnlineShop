package com.shalimov.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Builder
public class Product {
    private long id;
    private String name;
    private double price;
    private String description;
}