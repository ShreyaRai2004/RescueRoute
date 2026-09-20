package com.rescueroute.dto;
import jakarta.validation.constraints.NotNull;
public record DispatchRequest(@NotNull Long incidentId) {}
