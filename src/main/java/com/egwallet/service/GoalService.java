package com.egwallet.service;

import com.egwallet.dto.request.CreateGoalRequest;
import com.egwallet.dto.request.AddGoalContributionRequest;
import com.egwallet.dto.response.GoalResponse;
import com.egwallet.dto.response.GoalContributionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoalService {
    GoalResponse createGoal(Long userId, CreateGoalRequest request);
    GoalResponse getGoalById(Long userId, Long goalId);
    Page<GoalResponse> getAllGoals(Long userId, Pageable pageable);
    Page<GoalResponse> getGoalsByStatus(Long userId, String status, Pageable pageable);
    GoalResponse updateGoal(Long userId, Long goalId, CreateGoalRequest request);
    void deleteGoal(Long userId, Long goalId);
    GoalResponse addContribution(Long userId, Long goalId, AddGoalContributionRequest request);
    Page<GoalContributionResponse> getContributions(Long userId, Long goalId, Pageable pageable);
    List<GoalResponse> getOverdueGoals(Long userId);
    List<GoalResponse> getActiveGoals(Long userId);
}
