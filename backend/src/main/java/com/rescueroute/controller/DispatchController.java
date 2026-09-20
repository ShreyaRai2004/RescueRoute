package com.rescueroute.controller;
import com.rescueroute.dto.DispatchRequest;
import com.rescueroute.repository.AssignmentRepository;
import com.rescueroute.service.DispatchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api")
public class DispatchController {
    private final DispatchService service; private final AssignmentRepository assignments;
    public DispatchController(DispatchService s,AssignmentRepository a){service=s;assignments=a;}
    @PostMapping("/dispatch") public Object dispatch(@Valid @RequestBody DispatchRequest r){return service.dispatch(r.incidentId());}
    @GetMapping("/dispatches") public Object dispatches(){return assignments.findTop10ByOrderByAssignedAtDesc();}
    @GetMapping("/dispatches/{id}") public Object dispatch(@PathVariable Long id){
        return assignments.findById(id).orElseThrow(()->new RuntimeException("Dispatch not found"));
    }
}
