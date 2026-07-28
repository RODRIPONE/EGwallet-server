package com.egwallet.repository;

import com.egwallet.entity.DebtPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface DebtPaymentRepository extends JpaRepository<DebtPayment, Long> {
    @Query("SELECT dp FROM DebtPayment dp WHERE dp.debt.id = :debtId ORDER BY dp.paymentDate DESC")
    Page<DebtPayment> findByDebtId(@Param("debtId") Long debtId, Pageable pageable);
    
    @Query("SELECT SUM(dp.amount) FROM DebtPayment dp WHERE dp.debt.id = :debtId")
    BigDecimal sumByDebtId(@Param("debtId") Long debtId);
    
    @Query("SELECT SUM(dp.amount) FROM DebtPayment dp WHERE dp.debt.user.id = :userId AND dp.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal sumPaymentsByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
