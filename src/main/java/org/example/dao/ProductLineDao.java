package org.example.dao;

import org.example.model.ProductLine;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductLineDao {
    private final Connection connection;

    public ProductLineDao(Connection connection) {
        this.connection = connection;
    }

    public List<ProductLine> getAll() {
        List<ProductLine> list = new ArrayList<>();
        String sql = "SELECT id, name, description, start_date FROM ProductLine";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductLine pl = new ProductLine();
                pl.setId(rs.getInt("id"));
                pl.setName(rs.getString("name"));
                pl.setDescription(rs.getString("description"));
                Date date = rs.getDate("start_date");
                pl.setStartDate(date != null ? date.toLocalDate() : null);
                list.add(pl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ProductLine getById(int id) {
        String sql = "SELECT id, name, description, start_date FROM ProductLine WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductLine pl = new ProductLine();
                    pl.setId(rs.getInt("id"));
                    pl.setName(rs.getString("name"));
                    pl.setDescription(rs.getString("description"));
                    Date date = rs.getDate("start_date");
                    pl.setStartDate(date != null ? date.toLocalDate() : null);
                    return pl;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean create(ProductLine productLine) {
        String sql = "INSERT INTO ProductLine (name, description, start_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productLine.getName());
            ps.setString(2, productLine.getDescription());

            if (productLine.getStartDate() != null) {
                ps.setDate(3, Date.valueOf(productLine.getStartDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ProductLine productLine) {
        String sql = "UPDATE ProductLine SET name = ?, description = ?, start_date = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productLine.getName());
            ps.setString(2, productLine.getDescription());

            if (productLine.getStartDate() != null) {
                ps.setDate(3, Date.valueOf(productLine.getStartDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            ps.setInt(4, productLine.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM ProductLine WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
