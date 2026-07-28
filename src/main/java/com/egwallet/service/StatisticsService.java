package com.egwallet.service;

import com.egwallet.dto.response.StatisticsResponse;
import java.time.LocalDate;

public interface StatisticsService {
    StatisticsResponse getMonthlyStatistics(Long userId, int year, int month);
    StatisticsResponse getYearlyStatistics(Long userId, int year);
    StatisticsResponse getCustomPeriodStatistics(Long userId, LocalDate startDate, LocalDate endDate);
    StatisticsResponse.SummaryResponse getSummary(Long userId, LocalDate startDate, LocalDate endDate);
}
