package com.egwallet.repository;

import com.egwallet.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.deletedAt IS NULL ORDER BY t.transactionDate DESC, t.createdAt DESC")
    Page<Transaction> findByUserId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.account.id = :accountId AND t.deletedAt IS NULL ORDER BY t.transactionDate DESC")
    Page<Transaction> findByUserIdAndAccountId(@Param("userId") Long userId, @Param("accountId") Long accountId, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.category.id = :categoryId AND t.deletedAt IS NULL ORDER BY t.transactionDate DESC")
    Page<Transaction> findByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate AND t.deletedAt IS NULL ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserIdTypeAndDateRange(
        @Param("userId") Long userId,
        @Param("type") String type,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate AND t.deletedAt IS NULL")
    BigDecimal sumByUserIdTypeAndDateRange(
        @Param("userId") Long userId,
        @Param("type") String type,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.id = :transactionId AND t.deletedAt IS NULL")
    Optional<Transaction> findByIdAndUserId(@Param("transactionId") Long transactionId, @Param("userId") Long userId);
}
