package com.petproject.logistics.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Truck {
    private Long id;
    private String number;
    private int volume;
    private BigDecimal capacity;
}
