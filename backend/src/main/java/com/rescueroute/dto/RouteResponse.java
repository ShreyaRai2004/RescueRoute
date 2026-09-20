package com.rescueroute.dto;
import java.util.List;
public record RouteResponse(List<String> path, double distanceKm, double estimatedTimeMinutes) {}
