package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class Shipment {
    private int id;
    private int orderId;          // FK на Order.id (1:1)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippedDate;
    private String status;
    private int logisticsId;      // FK на LogisticsDepartment.id
}
