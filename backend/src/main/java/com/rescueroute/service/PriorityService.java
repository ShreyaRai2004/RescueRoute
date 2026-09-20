package com.rescueroute.service;
import com.rescueroute.entity.Incident;
import com.rescueroute.enums.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PriorityService {
    public PriorityQueue<Incident> buildQueue(List<Incident> incidents){
        PriorityQueue<Incident> queue=new PriorityQueue<>(
            Comparator.comparingInt((Incident i)->severityRank(i.getSeverity()))
                      .thenComparing(Incident::getCreatedAt)
        );
        incidents.stream().filter(i->i.getStatus()==IncidentStatus.PENDING).forEach(queue::add);
        return queue;
    }
    private int severityRank(Severity s){
        return switch(s){case CRITICAL->0; case HIGH->1; case MEDIUM->2; case LOW->3;};
    }
}
