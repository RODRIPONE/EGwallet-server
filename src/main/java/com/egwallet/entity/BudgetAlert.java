package com.egwallet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budget_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @Column(name = "alert_type", nullable = false)
    private String alertType; // THRESHOLD_REACHED, BUDGET_EXCEEDED

    @Column(name = "current_percentage")
    private Integer currentPercentage;

    @Column(name = "triggered_at")
    private java.time.LocalDateTime triggeredAt;

    @Column(name = "is_sent")
    private Boolean isSent = false;

    @Column(name = "sent_at")
    private java.time.LocalDateTime sentAt;
}
