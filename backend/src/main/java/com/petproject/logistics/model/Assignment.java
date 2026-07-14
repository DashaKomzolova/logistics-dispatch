package com.petproject.logistics.model;

import lombok.Data;

@Data
public class Assignment {
    private Long id;
    private Long loadId;
    private Long driverId;
    private Long truckId;
}
