package com.egwallet.repository;

import com.egwallet.entity.GoalContribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface GoalContributionRepository extends JpaRepository<GoalContribution, Long> {
    @Query("SELECT gc FROM GoalContribution gc WHERE gc.goal.id = :goalId ORDER BY gc.contributionDate DESC")
    Page<GoalContribution> findByGoalId(@Param("goalId") Long goalId, Pageable pageable);
    
    @Query("SELECT SUM(gc.amount) FROM GoalContribution gc WHERE gc.goal.id = :goalId")
    BigDecimal sumByGoalId(@Param("goalId") Long goalId);
    
    @Query("SELECT SUM(gc.amount) FROM GoalContribution gc WHERE gc.goal.user.id = :userId AND gc.contributionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumContributionsByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
