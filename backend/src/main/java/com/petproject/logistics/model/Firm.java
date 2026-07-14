package com.petproject.logistics.model;

import lombok.Data;

@Data
public class Firm {
    private Long id;
    private String name;
    private String atiFirmId;
    private String inn;
    private String phone;
}
