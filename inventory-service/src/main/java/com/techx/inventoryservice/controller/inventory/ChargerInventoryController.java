package com.techx.inventoryservice.controller.inventory;


import com.techx.dbhandler.models.inventoryservice.ChargerDetails;
import com.techx.dbhandler.repository.inventoryservice.ChargerDetailsRepository;
import com.techx.pojo.response.APIResponse;
import com.techx.pojo.response.inventory.charger.ChargerResponse;
import com.techx.pojo.response.inventory.price.ChargerPrice;
import com.techx.utilities.ResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/charger")
public class ChargerInventoryController {

    private Logger logger = LoggerFactory.getLogger(ChargerInventoryController.class);

    @Autowired
    private ChargerDetailsRepository chargerDetailsRepository;

    public ChargerInventoryController(ChargerDetailsRepository chargerDetailsRepository){
        this.chargerDetailsRepository = chargerDetailsRepository;
    }

    @GetMapping("/find")
    public ResponseEntity<APIResponse> getChargerByUDID(@RequestParam(name = "udid") String udid){

        ChargerDetails chargerDetails = chargerDetailsRepository.findByChargerUdid(udid);

        if(Objects.nonNull(chargerDetails)) {
            ChargerResponse chargerResponse = new ChargerResponse();
            chargerResponse.setId(chargerDetails.getId());
            chargerResponse.setChargerName(chargerDetails.getChargerName());
            chargerResponse.setUdid(chargerDetails.getChargerUdid());
            chargerResponse.setQrData(chargerDetails.getQrData());
            chargerResponse.setType(chargerDetails.getType());

            ChargerPrice chargerPrice = new ChargerPrice();
            chargerPrice.setPricingUdid(chargerDetails.getChargerPricingDetails().getPricingUdid());
            chargerPrice.setRatePerHour(chargerDetails.getChargerPricingDetails().getRatePerHr());
            chargerPrice.setRatePerMinute(chargerDetails.getChargerPricingDetails().getRatePerMin());
            chargerPrice.setId(chargerDetails.getChargerPricingDetails().getId());

            return ResponseUtility.createSuccessfulResponse("Chager Found", chargerResponse, HttpStatus.OK);
        }
        else{
            return ResponseUtility.createSuccessfulResponse("Chager NOT Found", "Charger with UDID : " + udid + " Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findall")
    public ResponseEntity<APIResponse> getChargers(){
        List<ChargerDetails> chargerDetails = chargerDetailsRepository.findAll();
        List<ChargerResponse> responses = new ArrayList<>();
        for(ChargerDetails charger : chargerDetails){
            ChargerResponse chargerResponse = new ChargerResponse();
            chargerResponse.setId(charger.getId());
            chargerResponse.setType(charger.getType());
            chargerResponse.setQrData(charger.getQrData());
            chargerResponse.setType(charger.getType());
            chargerResponse.setUdid(charger.getChargerUdid());
            chargerResponse.setChargerName(charger.getChargerName());

            ChargerPrice chargerPrice = new ChargerPrice();
            chargerPrice.setId(charger.getChargerPricingDetails().getId());
            chargerPrice.setChargerUdid(charger.getChargerPricingDetails().getChargerUdid());
            chargerPrice.setRatePerMinute(charger.getChargerPricingDetails().getRatePerMin());
            chargerPrice.setRatePerHour(charger.getChargerPricingDetails().getRatePerHr());
            chargerPrice.setPricingUdid(charger.getChargerPricingDetails().getPricingUdid());
            responses.add(chargerResponse);
        }
        return ResponseUtility.createSuccessfulResponse("All Details", responses, HttpStatus.OK);
    }
}
