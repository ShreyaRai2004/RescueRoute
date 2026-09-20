package com.rescueroute.dto;
import com.rescueroute.enums.*;
import jakarta.validation.constraints.NotNull;
public record VehicleStatusRequest(@NotNull VehicleStatus status) {}
