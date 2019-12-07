package com.techx.dbhandler.repository.inventoryservice;

import com.techx.dbhandler.models.inventoryservice.ChargerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerDetailsRepository extends JpaRepository<ChargerDetails, Long> {

    ChargerDetails findByChargerUdid(String chargerUdid);
    ChargerDetails findByChargerName(String chargerName);

}
