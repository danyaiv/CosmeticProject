package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LogisticsDepartment {
    private int id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private int assignedClient;   // FK на Client.id
    private String assignedClientName;
}