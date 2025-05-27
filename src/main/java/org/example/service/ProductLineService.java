package org.example.service;

import org.example.dao.ProductLineDao;
import org.example.model.ProductLine;

import java.sql.Connection;
import java.util.List;

public class ProductLineService {
    private final ProductLineDao dao;

    public ProductLineService(Connection connection) {
        this.dao = new ProductLineDao(connection);
    }

    public List<ProductLine> getAll() {
        return dao.getAll();
    }

    public ProductLine getById(int id) {
        return dao.getById(id);
    }

    public boolean create(ProductLine productLine) {
        return dao.create(productLine);
    }

    public boolean update(ProductLine productLine) {
        return dao.update(productLine);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }
}
