package com.rent.hatanotifier.service;

import com.rent.hatanotifier.entity.NotifierEntity;
import com.rent.hatanotifier.web.exception.EntityNotFoundException;
import com.rent.hatanotifier.web.exception.EntityToManyResultException;
import com.rent.hatanotifier.web.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotifierValidator {

    public static Optional<NotifierEntity> validateSearchResult(
            List<NotifierEntity> notifiers, UUID id) {
        if (notifiers.size() > 1) {
            throw new EntityToManyResultException(
                    String.format("More than one notifier found by id = %s", id));
        }
        if (notifiers.isEmpty()) {
            return Optional.empty();
        }
        return notifiers.stream().findFirst();
    }

    public static NotifierEntity validateNotifierCheckExists(
            Optional<NotifierEntity> notifier, UUID id) {
        return notifier.orElseThrow(() -> new EntityNotFoundException(
                String.format("Notifier not found by id = %s", id)));
    }

    public static void validateUpdateMarkResult(boolean update, UUID id) {
        if (!update) {
            throw new ValidationException(
                    String.format("Booking update mark failed by id = %s", id));
        }
    }

    public static void validateDeleteResult(boolean delete, UUID id) {
        if (!delete) {
            throw new ValidationException(
                    String.format("Notifier delete failed by id = %s", id));
        }
    }
}
