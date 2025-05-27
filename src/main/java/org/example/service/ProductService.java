package org.example.service;

import org.example.dao.ProductDao;
import org.example.model.Product;

import java.sql.Connection;
import java.util.List;

public class ProductService {
    private final ProductDao dao;

    public ProductService(Connection connection) {
        this.dao = new ProductDao(connection);
    }

    public List<Product> getAllProducts() {
        return dao.getAll();
    }

    public Product getProductById(int id) {
        return dao.getById(id);
    }

    public boolean createProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) return false;
        return dao.create(product);
    }

    public boolean updateProduct(Product product) {
        return dao.update(product);
    }

    public boolean deleteProduct(int id) {
        return dao.delete(id);
    }
}
