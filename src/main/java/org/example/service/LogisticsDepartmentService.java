package org.example.service;

import org.example.dao.ClientDao;
import org.example.dao.LogisticsDepartmentDao;
import org.example.model.Client;
import org.example.model.LogisticsDepartment;

import java.sql.Connection;
import java.util.List;

public class LogisticsDepartmentService {
    private final LogisticsDepartmentDao dao;
    private final ClientDao clientDao;

    public LogisticsDepartmentService(Connection connection) {
        this.dao = new LogisticsDepartmentDao(connection);
        this.clientDao = new ClientDao(connection);
    }

    public List<LogisticsDepartment> getAllDepartments() {
        List<LogisticsDepartment> departments = dao.getAll();

        for (LogisticsDepartment ld : departments) {
            Client client = clientDao.getById(ld.getAssignedClient());
            if (client != null) {
                ld.setAssignedClientName(client.getName());
            } else {
                ld.setAssignedClientName("Неизвестный клиент");
            }
        }

        return departments;
    }

    public LogisticsDepartment getDepartmentById(int id) {
        LogisticsDepartment ld = dao.getById(id);
        if (ld != null) {
            Client client = clientDao.getById(ld.getAssignedClient());
            if (client != null) {
                ld.setAssignedClientName(client.getName());
            } else {
                ld.setAssignedClientName("Неизвестный клиент");
            }
        }
        return ld;
    }

    public boolean createDepartment(LogisticsDepartment ld) {
        if (ld.getName() == null || ld.getName().isBlank()) return false;
        if (ld.getAssignedClient() <= 0) return false;
        return dao.create(ld);
    }

    public boolean updateDepartment(LogisticsDepartment ld) {
        return dao.update(ld);
    }

    public boolean deleteDepartment(int id) {
        return dao.delete(id);
    }
}
