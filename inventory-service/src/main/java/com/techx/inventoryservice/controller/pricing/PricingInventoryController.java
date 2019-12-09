package com.techx.inventoryservice.controller.pricing;


import com.techx.dbhandler.models.inventoryservice.ChargerDetails;
import com.techx.dbhandler.models.inventoryservice.ChargerPricingDetails;
import com.techx.dbhandler.repository.inventoryservice.ChargerDetailsRepository;
import com.techx.dbhandler.repository.inventoryservice.ChargerPricingRepository;
import com.techx.pojo.request.inventory.price.PriceRequest;
import com.techx.pojo.response.APIResponse;
import com.techx.pojo.response.inventory.price.ChargerPriceResponse;
import com.techx.utilities.AppUtilities;
import com.techx.utilities.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pricing")
public class PricingInventoryController {

    @Autowired
    private ChargerDetailsRepository chargerDetailsRepository;

    @Autowired
    private ChargerPricingRepository chargerPricingRepository;

    public PricingInventoryController(ChargerDetailsRepository chargerDetailsRepository, ChargerPricingRepository chargerPricingRepository){
        this.chargerDetailsRepository = chargerDetailsRepository;
        this.chargerPricingRepository = chargerPricingRepository;
    }

    @GetMapping("/find")
    public ResponseEntity<APIResponse> findPrice(@RequestParam(name = "pricingudid") String pricingUdid){
        ChargerPricingDetails chargerPricingDetails = chargerPricingRepository.findByPricingUdid(pricingUdid);
        if(Objects.isNull(chargerPricingDetails)){
            ChargerPriceResponse chargerPriceResponse = new ChargerPriceResponse();
            chargerPriceResponse.setId(chargerPricingDetails.getId());
            chargerPriceResponse.setChargerUdid(chargerPricingDetails.getChargerUdid());
            chargerPriceResponse.setPricingUdid(chargerPricingDetails.getPricingUdid());
            chargerPriceResponse.setRatePerHour(chargerPricingDetails.getRatePerHr());
            chargerPriceResponse.setRatePerMinute(chargerPricingDetails.getRatePerMin());
            return ResponseUtility.createSuccessfulResponse("Found", chargerPriceResponse, HttpStatus.OK);
        }
        else{
            return ResponseUtility.createFailureResponse("Cannot find price details with UDID : " + pricingUdid,
                    new ArrayList<String>(){{
                        add("Cannot find price details with UDID : " + pricingUdid);
                    }}, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findall")
    public ResponseEntity<APIResponse> findPrices(){
        List<ChargerPricingDetails> chargerPricingDetails = chargerPricingRepository.findAll();
        List<ChargerPriceResponse> responses = new ArrayList<>();
        for(ChargerPricingDetails priceDetails : chargerPricingDetails){
            ChargerPriceResponse chargerPriceResponse = new ChargerPriceResponse();
            chargerPriceResponse.setId(priceDetails.getId());
            chargerPriceResponse.setChargerUdid(priceDetails.getChargerUdid());
            chargerPriceResponse.setRatePerMinute(priceDetails.getRatePerMin());
            chargerPriceResponse.setRatePerHour(priceDetails.getRatePerHr());
            chargerPriceResponse.setPricingUdid(priceDetails.getPricingUdid());
            responses.add(chargerPriceResponse);
        }
        return ResponseUtility.createSuccessfulResponse("Pricing Details", responses, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createPrice(@RequestBody @Valid PriceRequest priceRequest){
        ChargerDetails chargerDetails = chargerDetailsRepository.findByChargerUdid(priceRequest.getCharegerUdid());
        if(Objects.nonNull(chargerDetails)){
            String udid = AppUtilities.generateUDID();
            ChargerPricingDetails chargerPricingDetails = new ChargerPricingDetails();
            chargerPricingDetails.setPricingUdid(udid);
            chargerPricingDetails.setChargerUdid(priceRequest.getCharegerUdid());
            chargerPricingDetails.setRatePerHr(priceRequest.getRatePerHr());
            chargerPricingDetails.setRatePerMin(priceRequest.getGetRatePerMin());
            chargerPricingRepository.save(chargerPricingDetails);

            chargerPricingDetails = chargerPricingRepository.findByPricingUdid(udid);

            ChargerPriceResponse chargerPriceResponse = new ChargerPriceResponse();
            chargerPriceResponse.setId(chargerPricingDetails.getId());
            chargerPriceResponse.setPricingUdid(chargerPricingDetails.getPricingUdid());
            chargerPriceResponse.setChargerUdid(chargerPricingDetails.getChargerUdid());
            chargerPriceResponse.setRatePerHour(chargerPricingDetails.getRatePerHr());
            chargerPriceResponse.setRatePerMinute(chargerPricingDetails.getRatePerMin());

            return ResponseUtility.createSuccessfulResponse("Created", chargerPriceResponse, HttpStatus.OK);
        }
        else{
            return ResponseUtility.createFailureResponse("Cannot find charger with UDID : " + chargerDetails,
                    new ArrayList<String>(){{
                        add(chargerDetails.getChargerUdid());
                        add("Please provide a valid charger id");
                    }},HttpStatus.NOT_FOUND);
        }
    }

}

