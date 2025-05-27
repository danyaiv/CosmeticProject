package org.example.service;

import org.example.dao.ClientDao;
import org.example.model.Client;

import java.sql.Connection;
import java.util.List;

public class ClientService {
    private final ClientDao dao;

    public ClientService(Connection connection) {
        this.dao = new ClientDao(connection);
    }

    public List<Client> getAllClients() {
        return dao.getAll();
    }

    public Client getClientById(int id) {
        return dao.getById(id);
    }

    public boolean createClient(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) return false;
        return dao.create(client);
    }

    public boolean updateClient(Client client) {
        return dao.update(client);
    }

    public boolean deleteClient(int id) {
        return dao.delete(id);
    }
}
