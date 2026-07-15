package com.petproject.logistics.mapper;

import com.petproject.logistics.model.Firm;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FirmRowMapper implements RowMapper<Firm> {
    @Override
    public Firm mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Firm firm = new Firm();

        firm.setId(resultSet.getLong("id"));
        firm.setName(resultSet.getString("name"));
        firm.setAtiFirmId(resultSet.getString("ati_firm_id"));
        firm.setInn(resultSet.getString("inn"));
        firm.setPhone(resultSet.getString("phone"));

        return firm;
    }
}
