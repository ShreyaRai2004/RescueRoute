
package com.rescueroute.repository;

import com.rescueroute.entity.Assignment;
import com.rescueroute.enums.AssignmentStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findTop10ByOrderByAssignedAtDesc();

    List<Assignment> findByStatusOrderByAssignedAtDesc(AssignmentStatus status);

    boolean existsByIncidentIdAndStatus(
            Long incidentId,
            AssignmentStatus status
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Assignment a where a.id = :id")
    Optional<Assignment> findByIdForUpdate(@Param("id") Long id);
}
