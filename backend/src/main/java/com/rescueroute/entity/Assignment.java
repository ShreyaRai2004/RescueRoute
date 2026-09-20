package com.rescueroute.entity;

import com.rescueroute.enums.AssignmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="assignments")
public class Assignment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private Long incidentId;
    @Column(nullable=false) private Long vehicleId;
    @Column(nullable=false) private double distance;
    @Column(nullable=false) private double estimatedTimeMinutes;
    @Column(nullable=false) private LocalDateTime assignedAt = LocalDateTime.now();
    private LocalDateTime completedAt;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private AssignmentStatus status = AssignmentStatus.ACTIVE;

    public Long getId(){return id;}
    public Long getIncidentId(){return incidentId;}
    public void setIncidentId(Long v){incidentId=v;}
    public Long getVehicleId(){return vehicleId;}
    public void setVehicleId(Long v){vehicleId=v;}
    public double getDistance(){return distance;}
    public void setDistance(double v){distance=v;}
    public double getEstimatedTimeMinutes(){return estimatedTimeMinutes;}
    public void setEstimatedTimeMinutes(double v){estimatedTimeMinutes=v;}
    public LocalDateTime getAssignedAt(){return assignedAt;}
    public LocalDateTime getCompletedAt(){return completedAt;}
    public void setCompletedAt(LocalDateTime v){completedAt=v;}
    public AssignmentStatus getStatus(){return status;}
    public void setStatus(AssignmentStatus v){status=v;}
}
