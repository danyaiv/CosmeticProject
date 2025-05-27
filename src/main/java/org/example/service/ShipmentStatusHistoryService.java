package org.example.service;

import org.example.dao.ShipmentStatusHistoryDao;
import org.example.model.ShipmentStatusHistory;

import java.sql.Connection;
import java.util.List;

public class ShipmentStatusHistoryService {
    private final ShipmentStatusHistoryDao dao;

    public ShipmentStatusHistoryService(Connection connection) {
        this.dao = new ShipmentStatusHistoryDao(connection);
    }

    public List<ShipmentStatusHistory> getAll() {
        return dao.getAll();
    }

    public ShipmentStatusHistory getById(int id) {
        return dao.getById(id);
    }

    public boolean create(ShipmentStatusHistory h) {
        return dao.create(h);
    }

    public boolean update(ShipmentStatusHistory h) {
        return dao.update(h);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }
}
