package com.rescueroute.dto;

import com.rescueroute.enums.*;
import jakarta.validation.constraints.*;
public record CreateIncidentRequest(
    @NotNull IncidentType type,
    @NotNull Severity severity,
    @NotBlank @Size(max=1000) String description,
    @DecimalMin("-90") @DecimalMax("90") double latitude,
    @DecimalMin("-180") @DecimalMax("180") double longitude
) {}
