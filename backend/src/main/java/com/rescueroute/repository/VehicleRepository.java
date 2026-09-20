package com.rescueroute.repository;
import com.rescueroute.entity.Vehicle;
import com.rescueroute.enums.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByStatus(VehicleStatus status);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from Vehicle v where v.id = :id")
    Optional<Vehicle> findByIdForUpdate(@Param("id") Long id);
}
