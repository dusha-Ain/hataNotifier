package com.rent.hatanotifier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rent.hatanotifier.dao.NotifierDao;
import com.rent.hatanotifier.dto.Notifier;
import com.rent.hatanotifier.dto.Notifiers;
import com.rent.hatanotifier.entity.NotifierEntity;
import com.rent.hatanotifier.entity.NotifiersEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class NotifierService {

    private final Logger logger = LoggerFactory.getLogger(NotifierService.class);
    private final NotifierDao notifierDao;
    private final NotifierMapper mapper;

    public NotifierService(NotifierDao notifierDao,
                           NotifierMapper mapper) {
        this.notifierDao = notifierDao;
        this.mapper = mapper;
    }


    public Notifiers findPending() {
        NotifiersEntity notifiers = notifierDao.findUnprocessedMessages();
        return new Notifiers(notifiers.list.stream()
                .map(mapper::toDomainNotifier).toList());
    }

    public Notifier findById(UUID id) {
        var notifier = NotifierValidator.validateNotifierCheckExists(
                notifierDao.findById(id), id);
        return mapper.toDomainNotifier(notifier);
    }

    public Notifiers findByAggregateId(String aggregateId) {
        NotifiersEntity notifiers = notifierDao.findByAggregateId(aggregateId);
        return new Notifiers(notifiers.list.stream()
                .map(mapper::toDomainNotifier).toList());
    }

    @Transactional
    public int processPendingMessages() {
        NotifiersEntity pending = notifierDao.findUnprocessedMessages();
        int processedCount = 0;

        for (NotifierEntity notifier : pending.list) {
            NotifierValidator.validateUpdateMarkResult(
                    notifierDao.markAsProcessed(notifier.getId()), notifier.getId());
            processedCount++;
        }
        return processedCount;
    }

    @Transactional
    public void deleteById(UUID id) {
        NotifierValidator.validateNotifierCheckExists(notifierDao.findByIdWithLock(id), id);
        NotifierValidator.validateDeleteResult(notifierDao.deleteById(id), id);
    }

    @Transactional
    public int deleteProcessedEvents() {
        return notifierDao.deleteProcessedEvents();
    }

    @Transactional
    public Notifier saveEvent(Notifier notifier) {
        logger.debug("Saving new notifier: type={}, aggregateId={}",
                notifier.type(), notifier.aggregateId());
        var notifierSave = mapper.toEntity(notifier);
        UUID id = null;
        try {
            id = notifierDao.save(notifierSave);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var savedNotifier = NotifierValidator.validateNotifierCheckExists(
                notifierDao.findById(id), id);
        return mapper.toDomainNotifier(savedNotifier);
    }

    @Transactional
    public void processPendingMessagesById(UUID messageId) {
        NotifierValidator.validateNotifierCheckExists(notifierDao.findByIdWithLock(messageId), messageId);
        NotifierValidator.validateUpdateMarkResult(
                notifierDao.markAsProcessed(messageId), messageId);
    }
}
