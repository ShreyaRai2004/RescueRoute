package com.rescueroute.service;
import com.rescueroute.dto.CreateIncidentRequest;
import com.rescueroute.entity.Incident;
import com.rescueroute.enums.IncidentStatus;
import com.rescueroute.exception.NotFoundException;
import com.rescueroute.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncidentService {
    private final IncidentRepository repository;
    public IncidentService(IncidentRepository repository){this.repository=repository;}

    public Incident create(CreateIncidentRequest r){
        Incident i=new Incident();
        i.setType(r.type()); i.setSeverity(r.severity()); i.setDescription(r.description());
        i.setLatitude(r.latitude()); i.setLongitude(r.longitude());
        return repository.save(i);
    }
    public List<Incident> all(){return repository.findAll();}
    public Incident get(Long id){return repository.findById(id).orElseThrow(()->new NotFoundException("Incident not found: "+id));}
    public Incident updateStatus(Long id,IncidentStatus status){
        Incident i=get(id); i.setStatus(status); return repository.save(i);
    }
    public List<Incident> pending(){return repository.findByStatus(IncidentStatus.PENDING);}
}
