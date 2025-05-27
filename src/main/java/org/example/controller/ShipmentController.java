package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Shipment;
import org.example.service.ShipmentService;

public class ShipmentController {
    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/shipments", this::getAll);
        app.get("/shipments/:id", this::getById);
        app.post("/shipments", this::create);
        app.put("/shipments/:id", this::update);
        app.delete("/shipments/:id", this::delete);
    }

    public void getAll(Context ctx) {
        ctx.json(service.getAllShipments());
    }

    public void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Shipment s = service.getShipmentById(id);
        if (s != null) {
            ctx.json(s);
        } else {
            ctx.status(404).result("Shipment not found");
        }
    }

    public void create(Context ctx) {
        Shipment s = ctx.bodyAsClass(Shipment.class);
        boolean created = service.createShipment(s);
        if (created) {
            ctx.status(201).json(s);
        } else {
            ctx.status(400).result("Invalid shipment data");
        }
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Shipment s = ctx.bodyAsClass(Shipment.class);
        s.setId(id);
        boolean updated = service.updateShipment(s);
        if (updated) {
            ctx.json(s);
        } else {
            ctx.status(404).result("Shipment not found");
        }
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.deleteShipment(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Shipment not found");
        }
    }
}
