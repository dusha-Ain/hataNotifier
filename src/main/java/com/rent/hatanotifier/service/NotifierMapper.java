package com.rent.hatanotifier.service;

import com.rent.hatanotifier.dto.Notifier;
import com.rent.hatanotifier.entity.NotifierEntity;
import com.rent.hatanotifier.web.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class NotifierMapper {

    public NotifierEntity toEntity(Notifier notifier) {
        if (notifier == null) {
            throw new ValidationException("notifier must be not null");
        }
        return new NotifierEntity(
                notifier.id(),
                notifier.aggregateId(),
                notifier.type(),
                notifier.payload(),
                notifier.createdAt(),
                notifier.processedAt()
        );
    }

    public Notifier toDomainNotifier(NotifierEntity notifierEntity){
        if (notifierEntity == null) {
            throw new ValidationException("notifier must be not null");
        }
        return new Notifier(
                notifierEntity.getId(),
                notifierEntity.getAggregateId(),
                notifierEntity.getType(),
                notifierEntity.getPayload(),
                notifierEntity.getCreatedAt(),
                notifierEntity.getProcessedAt()
        );
    }

}
