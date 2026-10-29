
package com.rescueroute.controller;

import com.rescueroute.dto.DispatchRequest;
import com.rescueroute.entity.Assignment;
import com.rescueroute.repository.AssignmentRepository;
import com.rescueroute.service.DispatchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DispatchController {

    private final DispatchService service;
    private final AssignmentRepository assignments;

    public DispatchController(
            DispatchService service,
            AssignmentRepository assignments) {
        this.service = service;
        this.assignments = assignments;
    }

    @PostMapping("/dispatch")
    public Object dispatch(@Valid @RequestBody DispatchRequest request) {
        return service.dispatch(request.incidentId());
    }

    @GetMapping("/dispatches")
    public Object dispatches() {
        return assignments.findTop10ByOrderByAssignedAtDesc();
    }

    @GetMapping("/dispatches/{id}")
    public Object getDispatch(@PathVariable Long id) {
        return assignments.findById(id)
                .orElseThrow(() ->
                        new com.rescueroute.exception.NotFoundException(
                                "Assignment not found: " + id));
    }

    @PutMapping("/dispatches/{id}/complete")
    public Assignment completeDispatch(@PathVariable Long id) {
        return service.completeAssignment(id);
    }
}
