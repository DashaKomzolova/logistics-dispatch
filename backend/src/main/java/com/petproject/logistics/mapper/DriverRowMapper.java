package com.petproject.logistics.mapper;

import com.petproject.logistics.model.Driver;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DriverRowMapper implements RowMapper<Driver> {
    @Override
    public Driver mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Driver driver = new Driver();

        driver.setId(resultSet.getLong("id"));
        driver.setFirstName(resultSet.getString("first_name"));
        driver.setLastName(resultSet.getString("last_name"));
        driver.setPerMileRate(resultSet.getBigDecimal("per_mile_rate"));
        driver.setPhone(resultSet.getString("phone"));
        driver.setTruckId(resultSet.getObject("truck_id", Long.class));

        return driver;
    }
}
