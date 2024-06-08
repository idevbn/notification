package com.ead.notification.dtos;

import com.ead.notification.controllers.UserNotificationController;
import com.ead.notification.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Record criado como exemplo demonstrado na aula
 * "Principais Features do Java 17 - Novidades e Exemplos".
 * <br>
 * Para utilizá-lo basta substituir o {@link NotificationDTO} em
 * {@link UserNotificationController} por este record.
 */
public record NotificationRecordDTO(@NotNull NotificationStatus notificationStatus) {
}
