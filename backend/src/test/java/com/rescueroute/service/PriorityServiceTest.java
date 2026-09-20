package com.rescueroute.service;
import com.rescueroute.entity.Incident;
import com.rescueroute.enums.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PriorityServiceTest {
    private Incident i(Severity s, LocalDateTime created){
        Incident i=new Incident();i.setSeverity(s);i.setStatus(IncidentStatus.PENDING);i.setCreatedAt(created);return i;
    }
    @Test void criticalBeforeHigh(){
        var now=LocalDateTime.now();
        var q=new PriorityService().buildQueue(java.util.List.of(i(Severity.HIGH,now.minusMinutes(30)),i(Severity.CRITICAL,now.minusMinutes(1))));
        assertEquals(Severity.CRITICAL,q.poll().getSeverity());
    }
    @Test void olderWinsSameSeverity(){
        var now=LocalDateTime.now();
        var older=i(Severity.HIGH,now.minusMinutes(30));
        var newer=i(Severity.HIGH,now.minusMinutes(1));
        var q=new PriorityService().buildQueue(java.util.List.of(newer,older));
        assertSame(older,q.poll());
    }
}
