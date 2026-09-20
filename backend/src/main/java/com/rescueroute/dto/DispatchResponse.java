package com.rescueroute.dto;
public record DispatchResponse(Long assignmentId, Long incidentId, Long vehicleId, String vehicleNumber,
                               double distanceKm, double estimatedTimeMinutes, String path, String message) {}
