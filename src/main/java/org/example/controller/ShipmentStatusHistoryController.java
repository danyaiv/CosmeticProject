package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.ShipmentStatusHistory;
import org.example.service.ShipmentStatusHistoryService;

public class ShipmentStatusHistoryController {
    private final ShipmentStatusHistoryService service;

    public ShipmentStatusHistoryController(ShipmentStatusHistoryService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/shipment-status-history", this::getAll);
        app.get("/shipment-status-history/:id", this::getById);
        app.post("/shipment-status-history", this::create);
        app.put("/shipment-status-history/:id", this::update);
        app.delete("/shipment-status-history/:id", this::delete);
    }

    public void getAll(Context ctx) {
        ctx.json(service.getAll());
    }

    public void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ShipmentStatusHistory h = service.getById(id);
        if (h != null) {
            ctx.json(h);
        } else {
            ctx.status(404).result("Status history not found");
        }
    }

    public void create(Context ctx) {
        ShipmentStatusHistory h = ctx.bodyAsClass(ShipmentStatusHistory.class);
        boolean created = service.create(h);
        if (created) {
            ctx.status(201).json(h);
        } else {
            ctx.status(400).result("Invalid data");
        }
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ShipmentStatusHistory h = ctx.bodyAsClass(ShipmentStatusHistory.class);
        h.setStatusHistoryId(id);
        boolean updated = service.update(h);
        if (updated) {
            ctx.json(h);
        } else {
            ctx.status(404).result("Not found");
        }
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.delete(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Not found");
        }
    }
}
