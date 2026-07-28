package com.egwallet.service;

import com.egwallet.dto.request.CreateAccountRequest;
import com.egwallet.dto.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(Long userId, CreateAccountRequest request);
    AccountResponse getAccountById(Long userId, Long accountId);
    Page<AccountResponse> getAllAccounts(Long userId, Pageable pageable);
    List<AccountResponse> getAllAccountsList(Long userId);
    AccountResponse updateAccount(Long userId, Long accountId, CreateAccountRequest request);
    void deleteAccount(Long userId, Long accountId);
    void archiveAccount(Long userId, Long accountId);
    AccountResponse setDefaultAccount(Long userId, Long accountId);
}
