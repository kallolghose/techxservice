package com.techx.dbhandler.repository.inventoryservice;

import com.techx.dbhandler.models.inventoryservice.ChargerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ChargerDetailsRepository extends JpaRepository<ChargerDetails, Long> {

    ChargerDetails findByChargerUdid(String chargerUdid);
    ChargerDetails findByChargerName(String chargerName);
    List<ChargerDetails> findByChargerNameContains(String chargerName);

}
