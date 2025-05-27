package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Client;
import org.example.service.ClientService;

public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/clients", this::getAllClients);
        app.get("/clients/:id", this::getClientById);
        app.post("/clients", this::createClient);
        app.put("/clients/:id", this::updateClient);
        app.delete("/clients/:id", this::deleteClient);
    }

    public void getAllClients(Context ctx) {
        ctx.json(service.getAllClients());
    }

    public void getClientById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Client client = service.getClientById(id);
        if (client != null) {
            ctx.json(client);
        } else {
            ctx.status(404).result("Client not found");
        }
    }

    public void createClient(Context ctx) {
        Client client = ctx.bodyAsClass(Client.class);
        if (service.createClient(client)) {
            ctx.status(201).json(client);
        } else {
            ctx.status(400).result("Invalid client data");
        }
    }

    public void updateClient(Context ctx) {
        String idStr = ctx.pathParam("id");
        System.out.println("updateClient called with id = " + idStr);

        int id = Integer.parseInt(idStr);
        Client client = ctx.bodyAsClass(Client.class);
        client.setId(id);
        boolean updated = service.updateClient(client);
        if (updated) {
            ctx.json(client);
        } else {
            ctx.status(404).result("Client not found");
        }
    }


    public void deleteClient(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.deleteClient(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Client not found");
        }
    }
}
