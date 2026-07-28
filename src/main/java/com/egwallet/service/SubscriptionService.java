package com.egwallet.service;

import com.egwallet.dto.response.SubscriptionResponse;
import com.egwallet.dto.response.StatisticsResponse;
import java.time.LocalDate;

public interface SubscriptionService {
    SubscriptionResponse getSubscription(Long userId);
    SubscriptionResponse upgradeToPremium(Long userId, String paymentMethod);
    void cancelSubscription(Long userId, String reason);
    void renewSubscription(Long userId);
    boolean isPremiumUser(Long userId);
    boolean canCreateResource(Long userId, String resourceType);
}
