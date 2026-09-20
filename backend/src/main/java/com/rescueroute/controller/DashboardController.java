package com.rescueroute.controller;

import com.rescueroute.enums.IncidentStatus;
import com.rescueroute.enums.VehicleStatus;
import com.rescueroute.enums.Severity;
import com.rescueroute.repository.IncidentRepository;
import com.rescueroute.repository.VehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final IncidentRepository incidents;
    private final VehicleRepository vehicles;

    public DashboardController(IncidentRepository incidents,
                               VehicleRepository vehicles) {
        this.incidents = incidents;
        this.vehicles = vehicles;
    }

    @GetMapping("/summary")
    public Object summary() {

        var allIncidents = incidents.findAll();
        List<com.rescueroute.entity.Vehicle> allVehicles = vehicles.findAll();

        long active = allIncidents.stream()
                .filter(i -> i.getStatus() != IncidentStatus.COMPLETED
                        && i.getStatus() != IncidentStatus.CANCELLED)
                .count();

        long critical = allIncidents.stream()
                .filter(i -> i.getSeverity() == Severity.CRITICAL
                        && i.getStatus() == IncidentStatus.PENDING)
                .count();

        long pending = allIncidents.stream()
                .filter(i -> i.getStatus() == IncidentStatus.PENDING)
                .count();

        long available = allVehicles.stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .count();

        long busy = allVehicles.stream()
                .filter(v -> v.getStatus() != VehicleStatus.AVAILABLE
                        && v.getStatus() != VehicleStatus.OFFLINE)
                .count();

        return Map.of(
                "activeEmergencies", active,
                "criticalEmergencies", critical,
                "pendingEmergencies", pending,
                "availableVehicles", available,
                "busyVehicles", busy
        );
    }
}