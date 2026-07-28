package com.egwallet.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // CASH, ORANGE_MONEY, MTN_MONEY, WAVE, BANK, OTHER

    @Column(nullable = false)
    private String currency = "XAF";

    @Column(name = "initial_balance")
    private BigDecimal initialBalance = BigDecimal.ZERO;

    @Column(name = "current_balance")
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(name = "icon_emoji")
    private String iconEmoji;

    @Column(name = "color_hex")
    private String colorHex;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
