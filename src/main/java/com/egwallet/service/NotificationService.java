package com.egwallet.service;

import com.egwallet.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    Page<NotificationResponse> getNotifications(Long userId, Pageable pageable);
    List<NotificationResponse> getUnreadNotifications(Long userId);
    long getUnreadCount(Long userId);
    void markAsRead(Long userId, Long notificationId);
    void markAllAsRead(Long userId);
    void deleteNotification(Long userId, Long notificationId);
    void deleteAllNotifications(Long userId);
    void sendNotification(Long userId, String type, String title, String message);
}
