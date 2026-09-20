package com.rescueroute.dto;
import jakarta.validation.constraints.NotBlank;
public record RouteRequest(@NotBlank String startNode, @NotBlank String endNode) {}
