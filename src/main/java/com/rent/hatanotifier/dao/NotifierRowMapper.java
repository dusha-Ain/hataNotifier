package com.rent.hatanotifier.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rent.hatanotifier.entity.NotifierEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Component
public class NotifierRowMapper implements RowMapper<NotifierEntity> {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotifierEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        var notifier = new NotifierEntity();
        notifier.setId(UUID.fromString(rs.getString("id")));
        notifier.setAggregateId(rs.getString("aggregate_id"));
        notifier.setType(rs.getString("type"));
        try {
            notifier.setPayload(objectMapper.readTree(rs.getString("payload")));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        notifier.setCreatedAt(rs.getTimestamp("created_at"));
        notifier.setProcessedAt(rs.getTimestamp("processed_at"));
        return notifier;
    }
}
