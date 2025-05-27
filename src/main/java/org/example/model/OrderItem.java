package org.example.model;

import lombok.Data;

@Data
public class OrderItem {
    private int id;
    private int orderId;    // FK на Order.id
    private int productId;  // FK на Product.id
    private int quantity;
}
