package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.ProductLine;
import org.example.service.ProductLineService;

public class ProductLineController {

    private final ProductLineService service;

    public ProductLineController(ProductLineService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/productlines", this::getAllProductLines);
        app.get("/productlines/:id", this::getProductLineById);
        app.post("/productlines", this::createProductLine);
        app.put("/productlines/:id", this::updateProductLine);
        app.delete("/productlines/:id", this::deleteProductLine);
    }

    public void getAllProductLines(Context ctx) {
        ctx.json(service.getAll());
    }

    public void getProductLineById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ProductLine pl = service.getById(id);
        if (pl != null) {
            ctx.json(pl);
        } else {
            ctx.status(404).result("ProductLine not found");
        }
    }

    public void createProductLine(Context ctx) {
        ProductLine pl = ctx.bodyAsClass(ProductLine.class);
        boolean created = service.create(pl);
        if (created) {
            ctx.status(201).json(pl);
        } else {
            ctx.status(400).result("Failed to create ProductLine");
        }
    }

    public void updateProductLine(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ProductLine pl = ctx.bodyAsClass(ProductLine.class);
        pl.setId(id);
        boolean updated = service.update(pl);
        if (updated) {
            ctx.json(pl);
        } else {
            ctx.status(404).result("ProductLine not found");
        }
    }

    public void deleteProductLine(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.delete(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("ProductLine not found");
        }
    }
}
