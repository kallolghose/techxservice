package com.techx.dbhandler.repository.inventoryservice;

import com.techx.dbhandler.models.inventoryservice.ChargingStationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChargingStationDetailsRepository extends JpaRepository<ChargingStationDetails, Long> {

    ChargingStationDetails findByStationUdid(String stationUdid);
    ChargingStationDetails findByStationName(String stationName);

    @Query(value = "SELECT * FROM chargingstationdetails c WHERE c.latitute = ?1 AND c.longitude = ?2", nativeQuery = true)
    ChargingStationDetails findByLatituteAndLogitude(Float latitute, Float longitude);

    List<ChargingStationDetails> findByState(String state);
    List<ChargingStationDetails> findByCity(String city);
    List<ChargingStationDetails> findByPincode(String pincode);
}
