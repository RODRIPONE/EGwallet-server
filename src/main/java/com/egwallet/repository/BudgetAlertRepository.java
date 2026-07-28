package com.egwallet.repository;

import com.egwallet.entity.BudgetAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetAlertRepository extends JpaRepository<BudgetAlert, Long> {
    @Query("SELECT ba FROM BudgetAlert ba WHERE ba.budget.id = :budgetId AND ba.isSent = false ORDER BY ba.triggeredAt DESC")
    List<BudgetAlert> findUnsentAlertsByBudgetId(@Param("budgetId") Long budgetId);
    
    @Query("SELECT ba FROM BudgetAlert ba WHERE ba.budget.user.id = :userId AND ba.isSent = false ORDER BY ba.triggeredAt DESC")
    List<BudgetAlert> findUnsentAlertsByUserId(@Param("userId") Long userId);
}
