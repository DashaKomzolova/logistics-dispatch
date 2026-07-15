package com.petproject.logistics.mapper;

import com.petproject.logistics.model.Truck;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TruckRowMapper implements RowMapper<Truck> {
    @Override
    public Truck mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Truck truck = new Truck();

        truck.setId(resultSet.getLong("id"));
        truck.setNumber(resultSet.getString("number"));
        truck.setVolume(resultSet.getInt("volume"));
        truck.setCapacity(resultSet.getBigDecimal("capacity"));

        return truck;
    }
}
