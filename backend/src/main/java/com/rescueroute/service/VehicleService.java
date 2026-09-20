package com.rescueroute.service;
import com.rescueroute.entity.Vehicle;
import com.rescueroute.enums.*;
import com.rescueroute.exception.*;
import com.rescueroute.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository repository;
    public VehicleService(VehicleRepository repository){this.repository=repository;}

    public List<Vehicle> all(){return repository.findAll();}
    public List<Vehicle> available(){return repository.findByStatus(VehicleStatus.AVAILABLE);}
    public Vehicle get(Long id){return repository.findById(id).orElseThrow(()->new NotFoundException("Vehicle not found: "+id));}

    public Vehicle updateStatus(Long id,VehicleStatus next){
        Vehicle v=get(id);
        if(!isValid(v.getStatus(),next)) throw new BusinessException("Invalid vehicle transition: "+v.getStatus()+" -> "+next);
        v.setStatus(next);
        return repository.save(v);
    }

    private boolean isValid(VehicleStatus from,VehicleStatus to){
        if(from==to) return true;
        return switch(from){
            case AVAILABLE -> to==VehicleStatus.DISPATCHED || to==VehicleStatus.OFFLINE;
            case DISPATCHED -> to==VehicleStatus.EN_ROUTE || to==VehicleStatus.AVAILABLE;
            case EN_ROUTE -> to==VehicleStatus.ON_SCENE;
            case ON_SCENE -> to==VehicleStatus.COMPLETED;
            case COMPLETED -> to==VehicleStatus.AVAILABLE;
            case OFFLINE -> to==VehicleStatus.AVAILABLE;
        };
    }
}
