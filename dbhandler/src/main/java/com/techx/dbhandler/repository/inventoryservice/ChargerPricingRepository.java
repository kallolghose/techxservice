package com.techx.dbhandler.repository.inventoryservice;

import com.techx.dbhandler.models.inventoryservice.ChargerPricingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerPricingRepository extends JpaRepository<ChargerPricingDetails, Long> {

    ChargerPricingDetails findByPricingUdid(String pricingUdid);

}
