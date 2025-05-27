package org.example.dao;

import org.example.model.Orders;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao {
    private final Connection connection;

    public OrdersDao(Connection connection) {
        this.connection = connection;
    }

    public List<Orders> getAll() {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT id, client_id, order_date, due_date, status FROM Orders";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Orders order = new Orders();
                order.setId(rs.getInt("id"));
                order.setClientId(rs.getInt("client_id"));
                Date orderDate = rs.getDate("order_date");
                if (orderDate != null) {
                    order.setOrderDate(orderDate.toLocalDate());
                }
                Date dueDate = rs.getDate("due_date");
                if (dueDate != null) {
                    order.setDueDate(dueDate.toLocalDate());
                }
                order.setStatus(rs.getString("status"));
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Orders getById(int id) {
        String sql = "SELECT id, client_id, order_date, due_date, status FROM Orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Orders order = new Orders();
                    order.setId(rs.getInt("id"));
                    order.setClientId(rs.getInt("client_id"));
                    Date orderDate = rs.getDate("order_date");
                    if (orderDate != null) {
                        order.setOrderDate(orderDate.toLocalDate());
                    }
                    Date dueDate = rs.getDate("due_date");
                    if (dueDate != null) {
                        order.setDueDate(dueDate.toLocalDate());
                    }
                    order.setStatus(rs.getString("status"));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean create(Orders order) {
        String sql = "INSERT INTO Orders (client_id, order_date, due_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getClientId());
            if (order.getOrderDate() != null) {
                ps.setDate(2, Date.valueOf(order.getOrderDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            if (order.getDueDate() != null) {
                ps.setDate(3, Date.valueOf(order.getDueDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, order.getStatus());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Orders order) {
        String sql = "UPDATE Orders SET client_id = ?, order_date = ?, due_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getClientId());
            if (order.getOrderDate() != null) {
                ps.setDate(2, Date.valueOf(order.getOrderDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            if (order.getDueDate() != null) {
                ps.setDate(3, Date.valueOf(order.getDueDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, order.getStatus());
            ps.setInt(5, order.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
