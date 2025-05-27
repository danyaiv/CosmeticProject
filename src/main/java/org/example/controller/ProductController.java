package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Product;
import org.example.service.ProductService;

public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("/products", this::getAllProducts);
        app.get("/products/:id", this::getProductById);
        app.post("/products", this::createProduct);
        app.put("/products/:id", this::updateProduct);
        app.delete("/products/:id", this::deleteProduct);
    }

    public void getAllProducts(Context ctx) {
        ctx.json(service.getAllProducts());
    }

    public void getProductById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Product product = service.getProductById(id);
        if (product != null) {
            ctx.json(product);
        } else {
            ctx.status(404).result("Product not found");
        }
    }

    public void createProduct(Context ctx) {
        Product product = ctx.bodyAsClass(Product.class);
        if (service.createProduct(product)) {
            ctx.status(201).json(product);
        } else {
            ctx.status(400).result("Invalid product data");
        }
    }

    public void updateProduct(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Product product = ctx.bodyAsClass(Product.class);
        product.setId(id);
        boolean updated = service.updateProduct(product);
        if (updated) {
            ctx.json(product);
        } else {
            ctx.status(404).result("Product not found");
        }
    }

    public void deleteProduct(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = service.deleteProduct(id);
        if (deleted) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Product not found");
        }
    }
}
