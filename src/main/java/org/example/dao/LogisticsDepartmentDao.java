package org.example.dao;

import org.example.model.LogisticsDepartment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogisticsDepartmentDao {
    private final Connection connection;

    public LogisticsDepartmentDao(Connection connection) {
        this.connection = connection;
    }

    public List<LogisticsDepartment> getAll() {
        List<LogisticsDepartment> list = new ArrayList<>();
        String sql = "SELECT id, name, created_date, assigned_client FROM logisticsdepartment";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LogisticsDepartment ld = new LogisticsDepartment();
                ld.setId(rs.getInt("id"));
                ld.setName(rs.getString("name"));

                Date date = rs.getDate("created_date");
                if (date != null) ld.setCreatedDate(date.toLocalDate());

                ld.setAssignedClient(rs.getInt("assigned_client"));
                list.add(ld);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public LogisticsDepartment getById(int id) {
        String sql = "SELECT id, name, created_date, assigned_client FROM logisticsdepartment WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LogisticsDepartment ld = new LogisticsDepartment();
                    ld.setId(rs.getInt("id"));
                    ld.setName(rs.getString("name"));

                    Date date = rs.getDate("created_date");
                    if (date != null) ld.setCreatedDate(date.toLocalDate());

                    ld.setAssignedClient(rs.getInt("assigned_client"));
                    return ld;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean create(LogisticsDepartment ld) {
        String sql = "INSERT INTO logistics_department (name, created_date, assignedclient) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ld.getName());
            if (ld.getCreatedDate() != null) {
                ps.setDate(2, Date.valueOf(ld.getCreatedDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setInt(3, ld.getAssignedClient());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(LogisticsDepartment ld) {
        String sql = "UPDATE logistics_department SET name = ?, created_date = ?, assignedclient = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ld.getName());
            if (ld.getCreatedDate() != null) {
                ps.setDate(2, Date.valueOf(ld.getCreatedDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setInt(3, ld.getAssignedClient());
            ps.setInt(4, ld.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM logisticsdepartment WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
