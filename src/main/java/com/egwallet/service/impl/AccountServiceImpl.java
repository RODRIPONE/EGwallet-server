package com.egwallet.service.impl;

import com.egwallet.dto.request.CreateAccountRequest;
import com.egwallet.dto.response.AccountResponse;
import com.egwallet.entity.Account;
import com.egwallet.entity.User;
import com.egwallet.exception.BadRequestException;
import com.egwallet.exception.ResourceNotFoundException;
import com.egwallet.repository.AccountRepository;
import com.egwallet.repository.UserRepository;
import com.egwallet.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AccountResponse createAccount(Long userId, CreateAccountRequest request) {
        log.info("Creating account for user: {}", userId);
        User user = userRepository.findByIdAndNotDeleted(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Account account = Account.builder()
                .user(user)
                .name(request.getName())
                .type(request.getType())
                .currency(request.getCurrency())
                .initialBalance(request.getInitialBalance())
                .currentBalance(request.getInitialBalance())
                .iconEmoji(request.getIconEmoji())
                .colorHex(request.getColorHex())
                .isDefault(request.getIsDefault())
                .bankName(request.getBankName())
                .accountNumber(request.getAccountNumber())
                .build();

        if (request.getIsDefault()) {
            accountRepository.findDefaultAccountByUserId(userId)
                    .ifPresent(defaultAccount -> {
                        defaultAccount.setIsDefault(false);
                        accountRepository.save(defaultAccount);
                    });
        }

        Account savedAccount = accountRepository.save(account);
        return mapToAccountResponse(savedAccount);
    }

    @Override
    public AccountResponse getAccountById(Long userId, Long accountId) {
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
        return mapToAccountResponse(account);
    }

    @Override
    public Page<AccountResponse> getAllAccounts(Long userId, Pageable pageable) {
        return accountRepository.findByUserIdAndNotArchived(userId, pageable)
                .map(this::mapToAccountResponse);
    }

    @Override
    public List<AccountResponse> getAllAccountsList(Long userId) {
        return accountRepository.findByUserIdAndNotArchivedList(userId)
                .stream()
                .map(this::mapToAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Long userId, Long accountId, CreateAccountRequest request) {
        log.info("Updating account: {} for user: {}", accountId, userId);
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        account.setName(request.getName());
        account.setType(request.getType());
        account.setCurrency(request.getCurrency());
        account.setIconEmoji(request.getIconEmoji());
        account.setColorHex(request.getColorHex());
        account.setBankName(request.getBankName());
        account.setAccountNumber(request.getAccountNumber());

        if (request.getIsDefault() && !account.getIsDefault()) {
            accountRepository.findDefaultAccountByUserId(userId)
                    .ifPresent(defaultAccount -> {
                        defaultAccount.setIsDefault(false);
                        accountRepository.save(defaultAccount);
                    });
            account.setIsDefault(true);
        }

        Account updatedAccount = accountRepository.save(account);
        return mapToAccountResponse(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long userId, Long accountId) {
        log.info("Deleting account: {} for user: {}", accountId, userId);
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public void archiveAccount(Long userId, Long accountId) {
        log.info("Archiving account: {} for user: {}", accountId, userId);
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
        account.setIsArchived(true);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountResponse setDefaultAccount(Long userId, Long accountId) {
        log.info("Setting default account: {} for user: {}", accountId, userId);
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        accountRepository.findDefaultAccountByUserId(userId)
                .ifPresent(defaultAccount -> {
                    defaultAccount.setIsDefault(false);
                    accountRepository.save(defaultAccount);
                });

        account.setIsDefault(true);
        Account updatedAccount = accountRepository.save(account);
        return mapToAccountResponse(updatedAccount);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .type(account.getType())
                .currency(account.getCurrency())
                .initialBalance(account.getInitialBalance())
                .currentBalance(account.getCurrentBalance())
                .iconEmoji(account.getIconEmoji())
                .colorHex(account.getColorHex())
                .isDefault(account.getIsDefault())
                .isArchived(account.getIsArchived())
                .bankName(account.getBankName())
                .accountNumber(account.getAccountNumber())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
