package com.petproject.logistics.mapper;

import com.petproject.logistics.model.City;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        City city = new City();

        city.setId(resultSet.getLong("id"));
        city.setName(resultSet.getString("name"));
        city.setRegion(resultSet.getString("region"));

        return city;
    }
}
