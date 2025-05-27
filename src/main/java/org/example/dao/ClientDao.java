package org.example.dao;

import org.example.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private final Connection connection;

    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, name, address, phone, email FROM Client";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setAddress(rs.getString("address"));
                client.setPhone(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Client getById(int id) {
        String sql = "SELECT id, name, address, phone, email FROM Client WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setName(rs.getString("name"));
                    client.setAddress(rs.getString("address"));
                    client.setPhone(rs.getString("phone"));
                    client.setEmail(rs.getString("email"));
                    return client;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean create(Client client) {
        String sql = "INSERT INTO Client (name, address, phone, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Client client) {
        String sql = "UPDATE Client SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());
            ps.setInt(5, client.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Client WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
