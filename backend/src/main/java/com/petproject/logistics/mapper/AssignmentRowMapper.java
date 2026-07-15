package com.petproject.logistics.mapper;

import com.petproject.logistics.model.Assignment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AssignmentRowMapper implements RowMapper<Assignment> {
    @Override
    public Assignment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Assignment assignment = new Assignment();
        assignment.setId(resultSet.getLong("id"));
        assignment.setLoadId(resultSet.getLong("load_id"));
        assignment.setDriverId(resultSet.getObject("driver_id", Long.class));
        assignment.setTruckId(resultSet.getObject("truck_id", Long.class));
        return assignment;
    }
}
