package com.petproject.logistics.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Load {
    private Long id;
    private String atiLoadId;
    private String cargoType;
    private BigDecimal weight;
    private BigDecimal volume;
    private Long loadingCityId;
    private Long unloadingCityId;
    private Long firmId;
    private BigDecimal rate;
    private LoadStatus status;
    private LocalDate firstDate;
    private LocalDate lastDate;
    private LocalDateTime syncedAt;
}
