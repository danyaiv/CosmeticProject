package org.example.service;

import org.example.dao.OrderItemDao;
import org.example.model.OrderItem;

import java.sql.Connection;
import java.util.List;

public class OrderItemService {
    private final OrderItemDao dao;

    public OrderItemService(Connection connection) {
        this.dao = new OrderItemDao(connection);
    }

    public List<OrderItem> getAllOrderItems() {
        return dao.getAll();
    }

    public OrderItem getOrderItemById(int id) {
        return dao.getById(id);
    }

    public boolean createOrderItem(OrderItem orderItem) {
        if (orderItem.getOrderId() <= 0 || orderItem.getProductId() <= 0 || orderItem.getQuantity() <= 0) {
            return false;
        }
        return dao.create(orderItem);
    }

    public boolean updateOrderItem(OrderItem orderItem) {
        return dao.update(orderItem);
    }

    public boolean deleteOrderItem(int id) {
        return dao.delete(id);
    }
}
