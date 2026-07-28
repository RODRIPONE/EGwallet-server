package com.egwallet.service;

import com.egwallet.dto.request.CreateTransactionRequest;
import com.egwallet.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(Long userId, CreateTransactionRequest request);
    TransactionResponse getTransactionById(Long userId, Long transactionId);
    Page<TransactionResponse> getAllTransactions(Long userId, Pageable pageable);
    Page<TransactionResponse> getTransactionsByAccount(Long userId, Long accountId, Pageable pageable);
    Page<TransactionResponse> getTransactionsByCategory(Long userId, Long categoryId, Pageable pageable);
    Page<TransactionResponse> getTransactionsByDateRange(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    TransactionResponse updateTransaction(Long userId, Long transactionId, CreateTransactionRequest request);
    void deleteTransaction(Long userId, Long transactionId);
    void reconcileTransaction(Long userId, Long transactionId);
}
