package org.example.dao;

import org.example.model.Shipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDao {
    private final Connection connection;

    public ShipmentDao(Connection connection) {
        this.connection = connection;
    }

    public List<Shipment> getAll() {
        List<Shipment> shipments = new ArrayList<>();
        String sql = "SELECT id, order_id, shipped_date, status, logistics_id FROM shipment";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shipment s = new Shipment();
                s.setId(rs.getInt("id"));
                s.setOrderId(rs.getInt("order_id"));
                Date date = rs.getDate("shipped_date");
                if (date != null) s.setShippedDate(date.toLocalDate());
                s.setStatus(rs.getString("status"));
                s.setLogisticsId(rs.getInt("logistics_id"));

                shipments.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shipments;
    }

    public Shipment getById(int id) {
        String sql = "SELECT id, order_id, shipped_date, status, logistics_id FROM shipment WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Shipment s = new Shipment();
                    s.setId(rs.getInt("id"));
                    s.setOrderId(rs.getInt("order_id"));
                    Date date = rs.getDate("shipped_date");
                    if (date != null) s.setShippedDate(date.toLocalDate());
                    s.setStatus(rs.getString("status"));
                    s.setLogisticsId(rs.getInt("logistics_id"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean create(Shipment s) {
        String sql = "INSERT INTO shipment (order_id, shipped_date, status, logistics_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, s.getOrderId());
            if (s.getShippedDate() != null) {
                ps.setDate(2, Date.valueOf(s.getShippedDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, s.getStatus());
            ps.setInt(4, s.getLogisticsId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Shipment s) {
        String sql = "UPDATE shipment SET order_id = ?, shipped_date = ?, status = ?, logistics_id = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, s.getOrderId());
            if (s.getShippedDate() != null) {
                ps.setDate(2, Date.valueOf(s.getShippedDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, s.getStatus());
            ps.setInt(4, s.getLogisticsId());
            ps.setInt(5, s.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM shipment WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
