package com.rescueroute.entity;

import com.rescueroute.enums.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private IncidentType type;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private Severity severity;
    @Column(nullable=false, length=1000)
    private String description;
    @Column(nullable=false) private double latitude;
    @Column(nullable=false) private double longitude;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private IncidentStatus status = IncidentStatus.PENDING;
    @Column(nullable=false) private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId(){return id;}
    public IncidentType getType(){return type;}
    public void setType(IncidentType type){this.type=type;}
    public Severity getSeverity(){return severity;}
    public void setSeverity(Severity severity){this.severity=severity;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}
    public double getLatitude(){return latitude;}
    public void setLatitude(double latitude){this.latitude=latitude;}
    public double getLongitude(){return longitude;}
    public void setLongitude(double longitude){this.longitude=longitude;}
    public IncidentStatus getStatus(){return status;}
    public void setStatus(IncidentStatus status){this.status=status;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
