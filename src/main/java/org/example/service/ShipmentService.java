package org.example.service;

import org.example.dao.ShipmentDao;
import org.example.model.Shipment;

import java.sql.Connection;
import java.util.List;

public class ShipmentService {
    private final ShipmentDao dao;

    public ShipmentService(Connection connection) {
        this.dao = new ShipmentDao(connection);
    }

    public List<Shipment> getAllShipments() {
        return dao.getAll();
    }

    public Shipment getShipmentById(int id) {
        return dao.getById(id);
    }

    public boolean createShipment(Shipment s) {
        if (s.getOrderId() <= 0 || s.getLogisticsId() <= 0 || s.getStatus() == null) return false;
        return dao.create(s);
    }

    public boolean updateShipment(Shipment s) {
        return dao.update(s);
    }

    public boolean deleteShipment(int id) {
        return dao.delete(id);
    }
}
