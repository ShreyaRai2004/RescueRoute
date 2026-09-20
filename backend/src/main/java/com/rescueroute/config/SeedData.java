package com.rescueroute.config;

import com.rescueroute.entity.*;
import com.rescueroute.enums.*;
import com.rescueroute.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedData {
    @Bean
    CommandLineRunner seed(VehicleRepository vehicles, IncidentRepository incidents){
        return args -> {
            if(vehicles.count()==0){
                vehicles.save(vehicle("AMB-001",VehicleType.AMBULANCE,12.9716,77.5946));
                vehicles.save(vehicle("AMB-002",VehicleType.AMBULANCE,12.9750,77.6000));
                vehicles.save(vehicle("AMB-003",VehicleType.AMBULANCE,12.9650,77.5900));
                vehicles.save(vehicle("FIRE-001",VehicleType.FIRE_TRUCK,12.9800,77.6100));
                vehicles.save(vehicle("FIRE-002",VehicleType.FIRE_TRUCK,12.9600,77.5850));
                vehicles.save(vehicle("RES-001",VehicleType.RESCUE_VEHICLE,12.9680,77.6050));
                vehicles.save(vehicle("RES-002",VehicleType.RESCUE_VEHICLE,12.9780,77.5800));
            }
            if(incidents.count()==0){
                incidents.save(incident(IncidentType.MEDICAL,Severity.CRITICAL,"Critical medical emergency",12.9725,77.5980));
                incidents.save(incident(IncidentType.FIRE,Severity.CRITICAL,"Building fire reported",12.9790,77.6060));
                incidents.save(incident(IncidentType.ACCIDENT,Severity.HIGH,"Road accident with injuries",12.9680,77.5920));
                incidents.save(incident(IncidentType.RESCUE,Severity.HIGH,"Rescue assistance required",12.9750,77.5840));
                incidents.save(incident(IncidentType.MEDICAL,Severity.MEDIUM,"Medical assistance",12.9630,77.6000));
                incidents.save(incident(IncidentType.ASSISTANCE,Severity.LOW,"Public assistance request",12.9700,77.6100));
            }
        };
    }
    private Vehicle vehicle(String n,VehicleType t,double lat,double lon){
        Vehicle v=new Vehicle();v.setVehicleNumber(n);v.setType(t);v.setLatitude(lat);v.setLongitude(lon);return v;
    }
    private Incident incident(IncidentType t,Severity s,String d,double lat,double lon){
        Incident i=new Incident();i.setType(t);i.setSeverity(s);i.setDescription(d);i.setLatitude(lat);i.setLongitude(lon);return i;
    }
}
