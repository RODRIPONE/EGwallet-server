package com.egwallet.service;

import com.egwallet.dto.request.CreateBudgetRequest;
import com.egwallet.dto.response.BudgetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BudgetService {
    BudgetResponse createBudget(Long userId, CreateBudgetRequest request);
    BudgetResponse getBudgetById(Long userId, Long budgetId);
    Page<BudgetResponse> getAllBudgets(Long userId, Pageable pageable);
    List<BudgetResponse> getActiveBudgets(Long userId);
    BudgetResponse updateBudget(Long userId, Long budgetId, CreateBudgetRequest request);
    void deleteBudget(Long userId, Long budgetId);
    void checkBudgetAlerts(Long userId);
    BudgetResponse getBudgetWithAlert(Long userId, Long budgetId);
}
