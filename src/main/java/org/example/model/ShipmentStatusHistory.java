package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShipmentStatusHistory {
    private int statusHistoryId;
    private int shipmentId;   // FK на Shipment.id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate changedAt;
    private String notes;
}
