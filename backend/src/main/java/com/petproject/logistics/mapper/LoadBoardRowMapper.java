package com.petproject.logistics.mapper;

import com.petproject.logistics.model.LoadBoard;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LoadBoardRowMapper implements RowMapper<LoadBoard> {
    @Override
    public LoadBoard mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        LoadBoard loadBoard = new LoadBoard();
        loadBoard.setLoadId(resultSet.getLong("load_id"));
        loadBoard.setBoardId(resultSet.getLong("board_id"));
        return loadBoard;
    }
}
