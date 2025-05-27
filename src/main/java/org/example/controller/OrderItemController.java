package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.OrderItem;
import org.example.service.OrderItemService;

public class OrderItemController {

    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/order-items", this::getAllOrderItems);
        app.get("/order-items/:id", this::getOrderItemById);
        app.post("/order-items", this::createOrderItem);
        app.put("/order-items/:id", this::updateOrderItem);
        app.delete("/order-items/:id", this::deleteOrderItem);
    }

    public void getAllOrderItems(Context ctx) {
        ctx.json(service.getAllOrderItems());
    }

    public void getOrderItemById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        OrderItem item = service.getOrderItemById(id);
        if (item != null) {
            ctx.json(item);
        } else {
            ctx.status(404).result("OrderItem not found");
        }
    }

    public void createOrderItem(Context ctx) {
        OrderItem item = ctx.bodyAsClass(OrderItem.class);
        boolean created = service.createOrderItem(item);
        if (created) {
            ctx.status(201).json(item);
        } else {
            ctx.status(400).result("Invalid order item data");
        }
    }

    public void updateOrderItem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        OrderItem item = ctx.bodyAsClass(OrderItem.class);
        item.setId(id);
        boolean updated = service.updateOrderItem(item);
        if (updated) {
            ctx.json(item);
        } else {
            ctx.status(404).result("OrderItem not found");
        }
    }

    public void deleteOrderItem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.deleteOrderItem(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("OrderItem not found");
        }
    }
}
