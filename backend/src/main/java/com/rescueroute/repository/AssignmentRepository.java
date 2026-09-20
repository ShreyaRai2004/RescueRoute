package com.rescueroute.repository;
import com.rescueroute.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findTop10ByOrderByAssignedAtDesc();
    boolean existsByIncidentIdAndStatus(Long incidentId, com.rescueroute.enums.AssignmentStatus status);
}
