package com.egwallet.service.impl;

import com.egwallet.dto.request.CreateTransactionRequest;
import com.egwallet.dto.response.TransactionResponse;
import com.egwallet.entity.Account;
import com.egwallet.entity.Category;
import com.egwallet.entity.Transaction;
import com.egwallet.entity.User;
import com.egwallet.exception.BadRequestException;
import com.egwallet.exception.ResourceNotFoundException;
import com.egwallet.repository.AccountRepository;
import com.egwallet.repository.CategoryRepository;
import com.egwallet.repository.TransactionRepository;
import com.egwallet.repository.UserRepository;
import com.egwallet.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransactionResponse createTransaction(Long userId, CreateTransactionRequest request) {
        log.info("Creating transaction for user: {}", userId);
        User user = userRepository.findByIdAndNotDeleted(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Account account = accountRepository.findByIdAndUserId(request.getAccountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", request.getAccountId()));

        Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Account relatedAccount = null;
        if ("TRANSFER".equals(request.getType()) && request.getRelatedAccountId() != null) {
            relatedAccount = accountRepository.findByIdAndUserId(request.getRelatedAccountId(), userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Related Account", "id", request.getRelatedAccountId()));
        }

        Transaction transaction = Transaction.builder()
                .user(user)
                .account(account)
                .category(category)
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .notes(request.getNotes())
                .transactionDate(request.getTransactionDate() != null ? request.getTransactionDate() : LocalDate.now())
                .transactionTime(request.getTransactionTime() != null ? request.getTransactionTime() : LocalTime.now())
                .recipientName(request.getRecipientName())
                .recipientPhone(request.getRecipientPhone())
                .paymentMethod(request.getPaymentMethod())
                .referenceNumber(request.getReferenceNumber())
                .receiptUrl(request.getReceiptUrl())
                .isRecurring(request.getIsRecurring())
                .recurrencePattern(request.getRecurrencePattern())
                .recurrenceEndDate(request.getRecurrenceEndDate())
                .relatedAccount(relatedAccount)
                .status("COMPLETED")
                .build();

        // Update account balance
        if ("INCOME".equals(request.getType())) {
            account.setCurrentBalance(account.getCurrentBalance().add(request.getAmount()));
        } else if ("EXPENSE".equals(request.getType())) {
            if (account.getCurrentBalance().compareTo(request.getAmount()) < 0) {
                throw new BadRequestException("Insufficient balance");
            }
            account.setCurrentBalance(account.getCurrentBalance().subtract(request.getAmount()));
        } else if ("TRANSFER".equals(request.getType())) {
            if (account.getCurrentBalance().compareTo(request.getAmount()) < 0) {
                throw new BadRequestException("Insufficient balance for transfer");
            }
            account.setCurrentBalance(account.getCurrentBalance().subtract(request.getAmount()));
            relatedAccount.setCurrentBalance(relatedAccount.getCurrentBalance().add(request.getAmount()));
            accountRepository.save(relatedAccount);
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToTransactionResponse(savedTransaction);
    }

    @Override
    public TransactionResponse getTransactionById(Long userId, Long transactionId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
        return mapToTransactionResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> getAllTransactions(Long userId, Pageable pageable) {
        return transactionRepository.findByUserId(userId, pageable)
                .map(this::mapToTransactionResponse);
    }

    @Override
    public Page<TransactionResponse> getTransactionsByAccount(Long userId, Long accountId, Pageable pageable) {
        return transactionRepository.findByUserIdAndAccountId(userId, accountId, pageable)
                .map(this::mapToTransactionResponse);
    }

    @Override
    public Page<TransactionResponse> getTransactionsByCategory(Long userId, Long categoryId, Pageable pageable) {
        return transactionRepository.findByUserIdAndCategoryId(userId, categoryId, pageable)
                .map(this::mapToTransactionResponse);
    }

    @Override
    public Page<TransactionResponse> getTransactionsByDateRange(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        // TODO: Implement pagination for date range
        return Page.empty(pageable);
    }

    @Override
    @Transactional
    public TransactionResponse updateTransaction(Long userId, Long transactionId, CreateTransactionRequest request) {
        log.info("Updating transaction: {} for user: {}", transactionId, userId);
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));

        Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        transaction.setCategory(category);
        transaction.setDescription(request.getDescription());
        transaction.setNotes(request.getNotes());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setTransactionTime(request.getTransactionTime());
        transaction.setRecipientName(request.getRecipientName());
        transaction.setRecipientPhone(request.getRecipientPhone());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setReferenceNumber(request.getReferenceNumber());
        transaction.setReceiptUrl(request.getReceiptUrl());

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return mapToTransactionResponse(updatedTransaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long userId, Long transactionId) {
        log.info("Deleting transaction: {} for user: {}", transactionId, userId);
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
        transaction.setDeletedAt(java.time.LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void reconcileTransaction(Long userId, Long transactionId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
        transaction.setIsReconciled(true);
        transactionRepository.save(transaction);
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .categoryId(transaction.getCategory().getId())
                .categoryName(transaction.getCategory().getName())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .notes(transaction.getNotes())
                .transactionDate(transaction.getTransactionDate())
                .transactionTime(transaction.getTransactionTime())
                .recipientName(transaction.getRecipientName())
                .recipientPhone(transaction.getRecipientPhone())
                .paymentMethod(transaction.getPaymentMethod())
                .referenceNumber(transaction.getReferenceNumber())
                .receiptUrl(transaction.getReceiptUrl())
                .isRecurring(transaction.getIsRecurring())
                .recurrencePattern(transaction.getRecurrencePattern())
                .recurrenceEndDate(transaction.getRecurrenceEndDate())
                .isReconciled(transaction.getIsReconciled())
                .status(transaction.getStatus())
                .relatedAccountId(transaction.getRelatedAccount() != null ? transaction.getRelatedAccount().getId() : null)
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
