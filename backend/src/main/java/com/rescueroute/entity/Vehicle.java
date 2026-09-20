package com.rescueroute.entity;

import com.rescueroute.enums.*;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true)
    private String vehicleNumber;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private VehicleType type;
    @Column(nullable=false) private double latitude;
    @Column(nullable=false) private double longitude;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private VehicleStatus status = VehicleStatus.AVAILABLE;
    private Long currentIncidentId;
    @Column(nullable=false) private int workload = 0;

    public Long getId(){return id;}
    public String getVehicleNumber(){return vehicleNumber;}
    public void setVehicleNumber(String v){vehicleNumber=v;}
    public VehicleType getType(){return type;}
    public void setType(VehicleType v){type=v;}
    public double getLatitude(){return latitude;}
    public void setLatitude(double v){latitude=v;}
    public double getLongitude(){return longitude;}
    public void setLongitude(double v){longitude=v;}
    public VehicleStatus getStatus(){return status;}
    public void setStatus(VehicleStatus v){status=v;}
    public Long getCurrentIncidentId(){return currentIncidentId;}
    public void setCurrentIncidentId(Long v){currentIncidentId=v;}
    public int getWorkload(){return workload;}
    public void setWorkload(int v){workload=v;}
}
