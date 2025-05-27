package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.LogisticsDepartment;
import org.example.service.LogisticsDepartmentService;

public class LogisticsDepartmentController {

    private final LogisticsDepartmentService service;

    public LogisticsDepartmentController(LogisticsDepartmentService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/logistics", this::getAll);
        app.get("/logistics/:id", this::getById);
        app.post("/logistics", this::create);
        app.put("/logistics/:id", this::update);
        app.delete("/logistics/:id", this::delete);
    }

    public void getAll(Context ctx) {
        ctx.json(service.getAllDepartments());
    }

    public void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        LogisticsDepartment ld = service.getDepartmentById(id);
        if (ld != null) {
            ctx.json(ld);
        } else {
            ctx.status(404).result("LogisticsDepartment not found");
        }
    }

    public void create(Context ctx) {
        LogisticsDepartment ld = ctx.bodyAsClass(LogisticsDepartment.class);
        boolean created = service.createDepartment(ld);
        if (created) {
            ctx.status(201).json(ld);
        } else {
            ctx.status(400).result("Invalid department data");
        }
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        LogisticsDepartment ld = ctx.bodyAsClass(LogisticsDepartment.class);
        ld.setId(id);
        boolean updated = service.updateDepartment(ld);
        if (updated) {
            ctx.json(ld);
        } else {
            ctx.status(404).result("LogisticsDepartment not found");
        }
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.deleteDepartment(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("LogisticsDepartment not found");
        }
    }
}
