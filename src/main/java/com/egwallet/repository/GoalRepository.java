package com.egwallet.repository;

import com.egwallet.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g WHERE g.user.id = :userId AND g.status = :status ORDER BY g.targetDate ASC")
    Page<Goal> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status, Pageable pageable);
    
    @Query("SELECT g FROM Goal g WHERE g.user.id = :userId AND g.status != 'CANCELLED' ORDER BY g.targetDate ASC")
    List<Goal> findByUserIdActive(@Param("userId") Long userId);
    
    @Query("SELECT g FROM Goal g WHERE g.user.id = :userId AND g.id = :goalId")
    Optional<Goal> findByIdAndUserId(@Param("goalId") Long goalId, @Param("userId") Long userId);
    
    @Query("SELECT g FROM Goal g WHERE g.user.id = :userId AND g.status = 'ACTIVE' AND g.targetDate < :today ORDER BY g.targetDate DESC")
    List<Goal> findOverdueGoals(@Param("userId") Long userId, @Param("today") LocalDate today);
}
