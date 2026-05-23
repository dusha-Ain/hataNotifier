package com.rent.hatanotifier.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rent.hatanotifier.entity.NotifierEntity;
import com.rent.hatanotifier.entity.NotifiersEntity;
import com.rent.hatanotifier.service.NotifierValidator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rent.hatanotifier.dao.NotifierRowMapper.objectMapper;

@Repository
public class NotifierDao {

    private final NamedParameterJdbcTemplate template;
    private final NotifierRowMapper rowMapper;

    public NotifierDao(NamedParameterJdbcTemplate template,
                       NotifierRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    public NotifiersEntity findUnprocessedMessages() {
        String sql = """
                    SELECT *
                    FROM outbox_messages
                    WHERE processed_at IS NULL
                    ORDER BY created_at
                """;
        return new NotifiersEntity(template.query(sql, rowMapper));
    }

    public Optional<NotifierEntity> findById(UUID id) {
        String sql = """
                    SELECT *
                    FROM outbox_messages
                    WHERE id = :id
                """;
        var map = new MapSqlParameterSource();
        map.addValue("id", id);
        List<NotifierEntity> notifiers = template.query(sql, map, rowMapper);
        return NotifierValidator.validateSearchResult(notifiers, id);

    }

    public Optional<NotifierEntity> findByIdWithLock(UUID id) {
        String sql = """
                    SELECT *
                    FROM outbox_messages
                    WHERE id = :id
                    FOR UPDATE
                """;
        var map = new MapSqlParameterSource();
        map.addValue("id", id);
        List<NotifierEntity> notifiers = template.query(sql, map, rowMapper);
        return NotifierValidator.validateSearchResult(notifiers, id);

    }

    public NotifiersEntity findByAggregateId(String aggregateId) {
        String sql = """
                    SELECT *
                    FROM outbox_messages
                    WHERE aggregate_id=:aggregateId
                    ORDER BY created_at DESC
                """;
        var map = new MapSqlParameterSource()
                .addValue("aggregateId", aggregateId);
        return new NotifiersEntity(template.query(sql, map, rowMapper));
    }

    public boolean markAsProcessed(UUID id) {
        String sql = """
                    UPDATE outbox_messages
                    SET processed_at = CURRENT_TIMESTAMP
                    WHERE id = :id AND processed_at IS NULL
                """;
        var map = new MapSqlParameterSource();
        map.addValue("id", id);
        return 1 == template.update(sql, map);
    }

    public boolean deleteById(UUID id) {
        String sql = "DELETE FROM outbox_messages WHERE id = :id";
        var map = new MapSqlParameterSource();
        map.addValue("id", id);
        return 1 == template.update(sql, map);
    }

    public int deleteProcessedEvents() {
        String sql = "DELETE FROM outbox_messages WHERE processed_at IS NOT NULL";
        return template.update(sql, new MapSqlParameterSource());
    }

    public UUID save(NotifierEntity notifier) throws JsonProcessingException {
        String sql = """
                    INSERT INTO outbox_messages (id, aggregate_id, type, payload, created_at, processed_at)
                    VALUES (:id, :aggregateId, :type, CAST(:payload AS JSONB), :createdAt, :processedAt)
                """;
        UUID id = UUID.randomUUID();
        String payloadJson = null;
        if (notifier.getPayload() != null) {
            payloadJson = objectMapper.writeValueAsString(notifier.getPayload());

        }
        var map = new MapSqlParameterSource();
        map.addValue("id", id);
        map.addValue("aggregateId", notifier.getAggregateId());
        map.addValue("type", notifier.getType());
        map.addValue("payload", payloadJson);
        map.addValue("createdAt", LocalDateTime.now());
        map.addValue("processedAt", notifier.getProcessedAt());
        return 1 == template.update(sql, map) ? id : null;
    }
}
