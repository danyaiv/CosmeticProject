package org.example.dao;

import org.example.model.ShipmentStatusHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentStatusHistoryDao {
    private final Connection connection;

    public ShipmentStatusHistoryDao(Connection connection) {
        this.connection = connection;
    }

    public List<ShipmentStatusHistory> getAll() {
        List<ShipmentStatusHistory> result = new ArrayList<>();
        String sql = "SELECT status_history_id, shipment_id, changed_at, notes FROM shipmentstatushistory";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ShipmentStatusHistory h = new ShipmentStatusHistory();
                h.setStatusHistoryId(rs.getInt("status_history_id"));
                h.setShipmentId(rs.getInt("shipment_id"));
                java.sql.Date sqlDate = rs.getDate("changed_at");
                if (sqlDate != null) h.setChangedAt(sqlDate.toLocalDate());
                h.setNotes(rs.getString("notes"));

                result.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ShipmentStatusHistory getById(int id) {
        String sql = "SELECT status_history_id, shipment_id, changed_at, notes FROM shipmentstatushistory WHERE status_history_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ShipmentStatusHistory h = new ShipmentStatusHistory();
                    h.setStatusHistoryId(rs.getInt("status_history_id"));
                    h.setShipmentId(rs.getInt("shipment_id"));
                    java.sql.Date sqlDate = rs.getDate("changed_at");
                    if (sqlDate != null) h.setChangedAt(sqlDate.toLocalDate());
                    h.setNotes(rs.getString("notes"));
                    return h;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean create(ShipmentStatusHistory h) {
        String sql = "INSERT INTO shipmentstatushistory (shipment_id, changed_at, notes) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, h.getShipmentId());
            ps.setDate(2, java.sql.Date.valueOf(h.getChangedAt()));
            ps.setString(3, h.getNotes());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ShipmentStatusHistory h) {
        String sql = "UPDATE shipmentstatushistory SET shipment_id = ?, changed_at = ?, notes = ? WHERE status_history_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, h.getShipmentId());
            ps.setDate(2, java.sql.Date.valueOf(h.getChangedAt()));
            ps.setString(3, h.getNotes());
            ps.setInt(4, h.getStatusHistoryId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM shipmentstatushistory WHERE status_history_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
