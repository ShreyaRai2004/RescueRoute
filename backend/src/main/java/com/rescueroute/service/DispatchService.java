package com.rescueroute.service;

import com.rescueroute.dto.DispatchResponse;
import com.rescueroute.entity.*;
import com.rescueroute.enums.*;
import com.rescueroute.exception.*;
import com.rescueroute.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class DispatchService {
    private final IncidentRepository incidentRepo;
    private final VehicleRepository vehicleRepo;
    private final AssignmentRepository assignmentRepo;
    private final PriorityService priorityService;
    private final RouteService routeService;

    public DispatchService(IncidentRepository i,VehicleRepository v,AssignmentRepository a,
                           PriorityService p,RouteService r){
        incidentRepo=i; vehicleRepo=v; assignmentRepo=a; priorityService=p; routeService=r;
    }

    @Transactional
    public DispatchResponse dispatch(Long incidentId){
        Incident incident=incidentRepo.findByIdForUpdate(incidentId)
                .orElseThrow(()->new NotFoundException("Incident not found: "+incidentId));
        if(incident.getStatus()!=IncidentStatus.PENDING)
            throw new BusinessException("Incident is not pending.");

        List<Vehicle> candidates=vehicleRepo.findByStatus(VehicleStatus.AVAILABLE).stream()
                .filter(v->compatible(v.getType(),incident.getType())).toList();

        if(candidates.isEmpty()) throw new BusinessException("No eligible vehicle is currently available.");

Vehicle candidate = candidates.stream()
        .min(
            Comparator
                .comparingDouble((Vehicle v) -> distance(v, incident))
                .thenComparingInt(Vehicle::getWorkload)
        )
        .orElseThrow();

        Vehicle vehicle=vehicleRepo.findByIdForUpdate(candidate.getId())
                .orElseThrow(()->new NotFoundException("Vehicle disappeared during dispatch."));
        if(vehicle.getStatus()!=VehicleStatus.AVAILABLE)
            throw new BusinessException("Selected vehicle is no longer available. Retry dispatch.");

        // Demonstration mapping from stored vehicle/incident records to graph nodes.
        String start=nodeFor(vehicle.getLatitude(),vehicle.getLongitude());
        String end=nodeFor(incident.getLatitude(),incident.getLongitude());
        var route=routeService.calculate(start,end);

        Assignment a=new Assignment();
        a.setIncidentId(incident.getId()); a.setVehicleId(vehicle.getId());
        a.setDistance(route.distanceKm()); a.setEstimatedTimeMinutes(route.estimatedTimeMinutes());
        Assignment saved=assignmentRepo.save(a);

        incident.setStatus(IncidentStatus.DISPATCHED);
        incidentRepo.save(incident);

        vehicle.setStatus(VehicleStatus.DISPATCHED);
        vehicle.setCurrentIncidentId(incident.getId());
        vehicle.setWorkload(vehicle.getWorkload()+1);
        vehicleRepo.save(vehicle);

        return new DispatchResponse(saved.getId(),incident.getId(),vehicle.getId(),vehicle.getVehicleNumber(),
                route.distanceKm(),route.estimatedTimeMinutes(),String.join(" → ",route.path()),
                "Dispatch created successfully.");
    }

    private boolean compatible(VehicleType v, IncidentType i){
        return switch(i){
            case MEDICAL -> v==VehicleType.AMBULANCE;
            case FIRE -> v==VehicleType.FIRE_TRUCK;
            case RESCUE, ACCIDENT -> v==VehicleType.RESCUE_VEHICLE || v==VehicleType.AMBULANCE;
            case ASSISTANCE -> true;
        };
    }

    private double distance(Vehicle v,Incident i){
        double dx=v.getLatitude()-i.getLatitude(), dy=v.getLongitude()-i.getLongitude();
        return Math.sqrt(dx*dx+dy*dy)*111.0;
    }

    private String nodeFor(double lat,double lon){
        // The demo UI uses fictional sample coordinates that map to the graph.
        int index=(int)Math.abs(Math.round((lat+lon)*10))%7;
        return String.valueOf((char)('A'+index));
    }
}
