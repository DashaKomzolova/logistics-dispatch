package com.petproject.logistics.mapper;

import com.petproject.logistics.model.Load;
import com.petproject.logistics.model.LoadStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class LoadRowMapper implements RowMapper<Load> {
    @Override
    public Load mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Load load = new Load();

        load.setId(resultSet.getLong("id"));
        load.setAtiLoadId(resultSet.getString("ati_load_id"));
        load.setCargoType(resultSet.getString("cargo_type"));
        load.setWeight(resultSet.getBigDecimal("weight"));
        load.setVolume(resultSet.getBigDecimal("volume"));
        load.setLoadingCityId(resultSet.getLong("loading_city_id"));
        load.setUnloadingCityId(resultSet.getLong("unloading_city_id"));
        load.setFirmId(resultSet.getObject("firm_id", Long.class));
        load.setRate(resultSet.getBigDecimal("rate"));
        load.setStatus(LoadStatus.valueOf(resultSet.getString("status").toUpperCase()));
        load.setFirstDate(resultSet.getObject("first_date", LocalDate.class));
        load.setLastDate(resultSet.getObject("last_date", LocalDate.class));
        load.setSyncedAt(resultSet.getObject("synced_at", LocalDateTime.class));

        return load;
    }
}
