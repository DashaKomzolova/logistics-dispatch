package com.petproject.logistics.mapper;

import com.petproject.logistics.model.Board;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BoardRowMapper implements RowMapper<Board> {
    @Override
    public Board mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Board board = new Board();

        board.setId(resultSet.getLong("id"));
        board.setAtiBoardId(resultSet.getString("ati_board_id"));
        board.setName(resultSet.getString("name"));
        board.setOwnerFirmId(resultSet.getLong("owner_firm_id"));

        return board;
    }
}
