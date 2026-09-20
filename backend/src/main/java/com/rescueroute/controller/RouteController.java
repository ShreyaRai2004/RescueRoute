package com.rescueroute.controller;
import com.rescueroute.dto.*;
import com.rescueroute.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/routes")
public class RouteController {
    private final RouteService service;
    public RouteController(RouteService s){service=s;}
    @PostMapping("/calculate") public RouteResponse calculate(@Valid @RequestBody RouteRequest r){return service.calculate(r.startNode(),r.endNode());}
}
