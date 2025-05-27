package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Orders;
import org.example.service.OrdersService;

public class OrdersController {

    private final OrdersService service;

    public OrdersController(OrdersService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/orders", this::getAllOrders);
        app.get("/orders/:id", this::getOrderById);
        app.post("/orders", this::createOrder);
        app.put("/orders/:id", this::updateOrder);
        app.delete("/orders/:id", this::deleteOrder);
    }

    public void getAllOrders(Context ctx) {
        ctx.json(service.getAllOrders());
    }

    public void getOrderById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Orders order = service.getOrderById(id);
        if (order != null) {
            ctx.json(order);
        } else {
            ctx.status(404).result("Order not found");
        }
    }

    public void createOrder(Context ctx) {
        Orders order = ctx.bodyAsClass(Orders.class);
        boolean created = service.createOrder(order);
        if (created) {
            ctx.status(201).json(order);
        } else {
            ctx.status(400).result("Invalid order data");
        }
    }

    public void updateOrder(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Orders order = ctx.bodyAsClass(Orders.class);
        order.setId(id);
        boolean updated = service.updateOrder(order);
        if (updated) {
            ctx.json(order);
        } else {
            ctx.status(404).result("Order not found");
        }
    }

    public void deleteOrder(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.deleteOrder(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Order not found");
        }
    }
}
