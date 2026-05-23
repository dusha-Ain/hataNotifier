package com.rent.hatanotifier.controller;

import com.rent.hatanotifier.dto.Notifier;
import com.rent.hatanotifier.dto.Notifiers;
import com.rent.hatanotifier.service.NotifierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notifiers/")
@Tag(name = "Уведомления", description = "Управление уведомлениями")
public class NotifierController {

    private final Logger logger = LoggerFactory.getLogger(NotifierController.class);
    private final NotifierService service;

    public NotifierController(NotifierService service) {
        this.service = service;
    }

    @Operation(summary = "Получить все необработанные события")
    @GetMapping("/pending")
    public ResponseEntity<Notifiers> getPending() {
        logger.info("Called getPending");
        return ResponseEntity.ok(service.findPending());
    }

    @Operation(summary = "Получить событие по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Notifier> getById(
            @PathVariable UUID id) {
        logger.info("Called getById = {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Получить все события по aggregateId")
    @GetMapping("/aggregate/{aggregateId}")
    public ResponseEntity<Notifiers> getByAggregateId(
            @PathVariable String aggregateId) {
        logger.info("Called getByAggregateId = {}", aggregateId);
        return ResponseEntity.ok(service.findByAggregateId(aggregateId));
    }

    @Operation(summary = "Ручной запуск обработки (для админов)")
    @PostMapping("/process")
    public ResponseEntity<String> processPending() {
        int count = service.processPendingMessages();
        logger.info("Called ProcessPending");
        return ResponseEntity.ok("Processed " + count + " messages");
    }

    @Operation(summary = "Пометить как обработанное")
    @PutMapping("/processed/{messageId}")
    public ResponseEntity<String> processPendingById(
            @PathVariable UUID messageId) {
        logger.info("Called ProcessPendingById = {}", messageId);
        service.processPendingMessagesById(messageId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Удалить событие (для очистки)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logger.info("Called delete by id = {}", id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить все обработанные")
    @DeleteMapping("/processed")
    public ResponseEntity<String> deleteProcessed() {
        logger.info("Called deleteProcessed");
        int deleted = service.deleteProcessedEvents();
        return ResponseEntity.ok("Deleted " + deleted + " processed events");
    }
}
