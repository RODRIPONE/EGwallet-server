package com.egwallet.repository;

import com.egwallet.entity.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.isActive = true ORDER BY b.startDate DESC")
    Page<Budget> findByUserIdAndActive(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.isActive = true AND b.period = :period ORDER BY b.startDate DESC")
    List<Budget> findByUserIdActivePeriod(@Param("userId") Long userId, @Param("period") String period);
    
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.id = :budgetId")
    Optional<Budget> findByIdAndUserId(@Param("budgetId") Long budgetId, @Param("userId") Long userId);
    
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.category.id = :categoryId AND b.period = :period AND b.isActive = true")
    Optional<Budget> findByCategoryAndPeriod(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId,
        @Param("period") String period
    );
    
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.isActive = true AND b.alertEnabled = true AND b.startDate <= :date AND (b.endDate IS NULL OR b.endDate >= :date)")
    List<Budget> findActiveBudgetsOnDate(
        @Param("userId") Long userId,
        @Param("date") LocalDate date
    );
}
