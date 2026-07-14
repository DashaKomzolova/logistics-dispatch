package com.petproject.logistics.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Driver {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal perMileRate;
    private String phone;
    private Long truckId;
}
