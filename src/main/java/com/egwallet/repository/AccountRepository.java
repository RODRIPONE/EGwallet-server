package com.egwallet.repository;

import com.egwallet.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.user.id = :userId AND a.isArchived = false ORDER BY a.isDefault DESC, a.createdAt DESC")
    Page<Account> findByUserIdAndNotArchived(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT a FROM Account a WHERE a.user.id = :userId AND a.isArchived = false ORDER BY a.isDefault DESC, a.createdAt DESC")
    List<Account> findByUserIdAndNotArchivedList(@Param("userId") Long userId);
    
    @Query("SELECT a FROM Account a WHERE a.user.id = :userId AND a.id = :accountId AND a.isArchived = false")
    Optional<Account> findByIdAndUserId(@Param("accountId") Long accountId, @Param("userId") Long userId);
    
    @Query("SELECT a FROM Account a WHERE a.user.id = :userId AND a.isDefault = true")
    Optional<Account> findDefaultAccountByUserId(@Param("userId") Long userId);
    
    long countByUserIdAndIsArchived(@Param("userId") Long userId, @Param("isArchived") Boolean isArchived);
}
