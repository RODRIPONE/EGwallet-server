package com.egwallet.repository;

import com.egwallet.entity.Debt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    @Query("SELECT d FROM Debt d WHERE d.user.id = :userId AND d.isArchived = false ORDER BY d.dueDate ASC NULLS LAST, d.createdAt DESC")
    Page<Debt> findByUserIdAndNotArchived(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT d FROM Debt d WHERE d.user.id = :userId AND d.type = :type AND d.isArchived = false ORDER BY d.dueDate ASC NULLS LAST")
    List<Debt> findByUserIdTypeAndNotArchived(@Param("userId") Long userId, @Param("type") String type);
    
    @Query("SELECT d FROM Debt d WHERE d.user.id = :userId AND d.status = :status AND d.isArchived = false ORDER BY d.dueDate ASC")
    List<Debt> findByUserIdStatusAndNotArchived(@Param("userId") Long userId, @Param("status") String status);
    
    @Query("SELECT d FROM Debt d WHERE d.user.id = :userId AND d.id = :debtId")
    Optional<Debt> findByIdAndUserId(@Param("debtId") Long debtId, @Param("userId") Long userId);
    
    @Query("SELECT SUM(d.remainingAmount) FROM Debt d WHERE d.user.id = :userId AND d.type = :type AND d.isArchived = false")
    BigDecimal sumRemainingByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
    
    @Query("SELECT d FROM Debt d WHERE d.user.id = :userId AND d.dueDate < :today AND d.status != 'PAID' AND d.isArchived = false")
    List<Debt> findOverdueDebts(@Param("userId") Long userId, @Param("today") LocalDate today);
}
