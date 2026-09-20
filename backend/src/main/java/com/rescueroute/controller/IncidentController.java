package com.rescueroute.controller;
import com.rescueroute.dto.*;
import com.rescueroute.service.*;
import com.rescueroute.enums.IncidentStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService service; private final PriorityService priority;
    public IncidentController(IncidentService s,PriorityService p){service=s;priority=p;}
    @PostMapping public Object create(@Valid @RequestBody CreateIncidentRequest r){return service.create(r);}
    @GetMapping public Object all(){return service.all();}
    @GetMapping("/{id}") public Object get(@PathVariable Long id){return service.get(id);}
    @PutMapping("/{id}/status") public Object status(@PathVariable Long id,@Valid @RequestBody StatusRequest r){return service.updateStatus(id,r.status());}
    @GetMapping("/priority-queue") public Object queue(){
        var q=priority.buildQueue(service.pending()); List<Object> out=new ArrayList<>();
        while(!q.isEmpty()){var i=q.poll(); out.add(Map.of("id",i.getId(),"type",i.getType(),"severity",i.getSeverity(),"createdAt",i.getCreatedAt()));}
        return out;
    }
}
