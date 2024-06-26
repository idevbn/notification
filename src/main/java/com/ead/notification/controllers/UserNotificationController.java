package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDTO;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationService notificationService;

    public UserNotificationController(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(
            @PathVariable(value = "userId") final UUID userId,
            @PageableDefault(
                    page = 0, size = 10, sort = "notificationId",
                    direction = Sort.Direction.ASC
            ) final Pageable pageable
            ) {
        final Page<NotificationModel> notificationsByUser = this.notificationService
                .findAllNotificationsByUser(userId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(notificationsByUser);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(
            @PathVariable(value = "userId") final UUID userId,
            @PathVariable(value = "notificationId") final UUID notificationId,
            @RequestBody @Valid final NotificationDTO notificationDTO
    ) {
        final Optional<NotificationModel> notificationModelOpt = this.notificationService
                .findByNotificationIdAndUserId(notificationId, userId);

        if (notificationModelOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }

        notificationModelOpt.get().setNotificationStatus(notificationDTO.getNotificationStatus());

        final NotificationModel notificationModel = this.notificationService
                .saveNotification(notificationModelOpt.get());

        return ResponseEntity.status(HttpStatus.OK).body(notificationModel);
    }

}
