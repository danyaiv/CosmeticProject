package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.example.controller.*;
import org.example.service.*;
import org.example.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class CosmeticApp {

    public static void main(String[] args) throws SQLException {

        // Получаем соединение с базой данных
        Connection connection = DbUtil.getConnection();

        // Создаем экземпляры контроллеров, передаем connection в конструкторы
        ClientService clientService = new ClientService(connection);
        ClientController clientController = new ClientController(clientService);

        ProductService productService = new ProductService(connection);
        ProductController productController = new ProductController(productService);

        ProductLineService productLineService = new ProductLineService(connection);
        ProductLineController productLineController = new ProductLineController(productLineService);

        OrdersService ordersService = new OrdersService(connection);
        OrdersController ordersController = new OrdersController(ordersService);

        OrderItemService orderItemService = new OrderItemService(connection);
        OrderItemController orderItemController = new OrderItemController(orderItemService);

        ShipmentService shipmentService = new ShipmentService(connection);
        ShipmentController shipmentController = new ShipmentController(shipmentService);

        LogisticsDepartmentService logisticsDepartmentService = new LogisticsDepartmentService(connection);
        LogisticsDepartmentController logisticsDepartmentController = new LogisticsDepartmentController(logisticsDepartmentService);

        ShipmentStatusHistoryService shipmentStatusHistoryService = new ShipmentStatusHistoryService(connection);
        ShipmentStatusHistoryController shipmentStatusHistoryController = new ShipmentStatusHistoryController(shipmentStatusHistoryService);

        // Настройка ObjectMapper для поддержки LocalDateTime
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JavalinJackson jsonMapper = new JavalinJackson(mapper);

        // Создаем и запускаем Javalin сервер
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.directory = "frontend"; // путь к HTML-файлам
                staticFileConfig.hostedPath = "/";
            });
            config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
            config.jsonMapper(jsonMapper); // <-- Устанавливаем кастомный JSON-маршрутизатор
        }).start(7070);

        // --- Роуты для Клиентов ---
        app.get("/clients", clientController::getAllClients);
        app.get("/clients/{id}", clientController::getClientById);
        app.post("/clients", clientController::createClient);
        app.put("/clients/{id}", clientController::updateClient);
        app.delete("/clients/{id}", clientController::deleteClient);

        // --- Роуты для Товаров ---
        app.get("/products", productController::getAllProducts);
        app.get("/products/{id}", productController::getProductById);
        app.post("/products", productController::createProduct);
        app.put("/products/{id}", productController::updateProduct);
        app.delete("/products/{id}", productController::deleteProduct);

        // --- Роуты для Линеек продукции ---
        app.get("/productLines", productLineController::getAllProductLines);
        app.get("/productLines/{id}", productLineController::getProductLineById);
        app.post("/productLines", productLineController::createProductLine);
        app.put("/productLines/{id}", productLineController::updateProductLine);
        app.delete("/productLines/{id}", productLineController::deleteProductLine);

        // --- Роуты для Заказов ---
        app.get("/orders", ordersController::getAllOrders);
        app.get("/orders/{id}", ordersController::getOrderById);
        app.post("/orders", ordersController::createOrder);
        app.put("/orders/{id}", ordersController::updateOrder);
        app.delete("/orders/{id}", ordersController::deleteOrder);

        // --- Роуты для Позиции заказа ---
        app.get("/orderItems", orderItemController::getAllOrderItems);
        app.get("/orderItems/{id}", orderItemController::getOrderItemById);
        app.post("/orderItems", orderItemController::createOrderItem);
        app.put("/orderItems/{id}", orderItemController::updateOrderItem);
        app.delete("/orderItems/{id}", orderItemController::deleteOrderItem);

        // --- Роуты для Доставок ---
        app.get("/shipments", shipmentController::getAll);
        app.get("/shipments/{id}", shipmentController::getById);
        app.post("/shipments", shipmentController::create);
        app.put("/shipments/{id}", shipmentController::update);
        app.delete("/shipments/{id}", shipmentController::delete);

        // --- Роуты для Логистических отделов ---
        app.get("/logisticsDepartments", logisticsDepartmentController::getAll);
        app.get("/logisticsDepartments/{id}", logisticsDepartmentController::getById);
        app.post("/logisticsDepartments", logisticsDepartmentController::create);
        app.put("/logisticsDepartments/{id}", logisticsDepartmentController::update);
        app.delete("/logisticsDepartments/{id}", logisticsDepartmentController::delete);

        // --- Роуты для Истории статусов доставки ---
        app.get("/shipmentStatusHistory", shipmentStatusHistoryController::getAll);
        app.get("/shipmentStatusHistory/{id}", shipmentStatusHistoryController::getById);
        app.post("/shipmentStatusHistory", shipmentStatusHistoryController::create);
        app.put("/shipmentStatusHistory/{id}", shipmentStatusHistoryController::update);
        app.delete("/shipmentStatusHistory/{id}", shipmentStatusHistoryController::delete);

        System.out.println("Server started at http://localhost:7070");
    }
}
