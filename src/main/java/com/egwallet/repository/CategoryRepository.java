package com.egwallet.repository;

import com.egwallet.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.isDeleted = false ORDER BY c.isDefault DESC, c.name ASC")
    Page<Category> findByUserIdAndNotDeleted(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.type = :type AND c.isDeleted = false ORDER BY c.isDefault DESC, c.name ASC")
    Page<Category> findByUserIdTypeAndNotDeleted(@Param("userId") Long userId, @Param("type") String type, Pageable pageable);
    
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.isDeleted = false ORDER BY c.isDefault DESC, c.name ASC")
    List<Category> findByUserIdAndNotDeletedList(@Param("userId") Long userId);
    
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.id = :categoryId AND c.isDeleted = false")
    Optional<Category> findByIdAndUserId(@Param("categoryId") Long categoryId, @Param("userId") Long userId);
    
    @Query("SELECT c FROM Category c WHERE c.isDefault = true AND c.type = :type AND c.isDeleted = false")
    List<Category> findDefaultCategories(@Param("type") String type);
}
