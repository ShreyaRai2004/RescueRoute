package com.rescueroute.controller;
import com.rescueroute.dto.VehicleStatusRequest;
import com.rescueroute.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService service;
    public VehicleController(VehicleService s){service=s;}
    @GetMapping public Object all(){return service.all();}
    @GetMapping("/available") public Object available(){return service.available();}
    @GetMapping("/{id}") public Object get(@PathVariable Long id){return service.get(id);}
    @PutMapping("/{id}/status") public Object status(@PathVariable Long id,@Valid @RequestBody VehicleStatusRequest r){return service.updateStatus(id,r.status());}
}
