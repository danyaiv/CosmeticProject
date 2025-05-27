package org.example.service;

import org.example.dao.OrdersDao;
import org.example.model.Orders;
import org.example.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrdersService {
    public OrdersService(Connection connection) {
        this.dao = new OrdersDao(connection);
    }
    private final OrdersDao dao;

    public OrdersService() throws SQLException {
        Connection conn = DbUtil.getConnection();
        this.dao = new OrdersDao(conn);
    }

    public List<Orders> getAllOrders() {
        return dao.getAll();
    }

    public Orders getOrderById(int id) {
        return dao.getById(id);
    }

    public boolean createOrder(Orders order) {
        if (order.getClientId() <= 0) return false;
        return dao.create(order);
    }

    public boolean updateOrder(Orders order) {
        return dao.update(order);
    }

    public boolean deleteOrder(int id) {
        return dao.delete(id);
    }
}
