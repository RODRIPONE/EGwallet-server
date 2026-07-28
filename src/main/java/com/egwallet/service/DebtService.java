package com.egwallet.service;

import com.egwallet.dto.request.CreateDebtRequest;
import com.egwallet.dto.request.AddDebtPaymentRequest;
import com.egwallet.dto.response.DebtResponse;
import com.egwallet.dto.response.DebtPaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DebtService {
    DebtResponse createDebt(Long userId, CreateDebtRequest request);
    DebtResponse getDebtById(Long userId, Long debtId);
    Page<DebtResponse> getAllDebts(Long userId, Pageable pageable);
    Page<DebtResponse> getDebtsByType(Long userId, String type, Pageable pageable);
    Page<DebtResponse> getDebtsByStatus(Long userId, String status, Pageable pageable);
    DebtResponse updateDebt(Long userId, Long debtId, CreateDebtRequest request);
    void deleteDebt(Long userId, Long debtId);
    void archiveDebt(Long userId, Long debtId);
    DebtResponse addPayment(Long userId, Long debtId, AddDebtPaymentRequest request);
    Page<DebtPaymentResponse> getPayments(Long userId, Long debtId, Pageable pageable);
    List<DebtResponse> getOverdueDebts(Long userId);
}
