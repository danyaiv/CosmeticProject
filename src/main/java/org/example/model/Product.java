package org.example.model;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private String unit;
    private double price;
    private Integer lineId;  // FK на ProductLine.id
}
