package com.petproject.logistics.model;

import lombok.Data;

@Data
public class Board {
    private Long id;
    private String atiBoardId;
    private String name;
    private Long ownerFirmId;
}
