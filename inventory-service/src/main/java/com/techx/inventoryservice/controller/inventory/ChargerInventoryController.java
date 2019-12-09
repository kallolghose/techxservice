package com.techx.inventoryservice.controller.inventory;


import com.techx.dbhandler.models.inventoryservice.ChargerDetails;
import com.techx.dbhandler.repository.inventoryservice.ChargerDetailsRepository;
import com.techx.pojo.request.inventory.charger.ChargerRequest;
import com.techx.pojo.response.APIResponse;
import com.techx.pojo.response.inventory.charger.ChargerResponse;
import com.techx.pojo.response.inventory.price.ChargerPriceResponse;
import com.techx.utilities.AppUtilities;
import com.techx.utilities.ResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/charger")
public class ChargerInventoryController {

    private Logger logger = LoggerFactory.getLogger(ChargerInventoryController.class);

    @Autowired
    private ChargerDetailsRepository chargerDetailsRepository;

    public ChargerInventoryController(ChargerDetailsRepository chargerDetailsRepository){
        this.chargerDetailsRepository = chargerDetailsRepository;
    }

    @GetMapping("/findbyudid/{udid}")
    public ResponseEntity<APIResponse> getChargerByUDID(@PathVariable("udid") String udid){

        ChargerDetails chargerDetails = chargerDetailsRepository.findByChargerUdid(udid);

        if(Objects.nonNull(chargerDetails)) {
            ChargerResponse chargerResponse = new ChargerResponse();
            chargerResponse.setId(chargerDetails.getId());
            chargerResponse.setChargerName(chargerDetails.getChargerName());
            chargerResponse.setUdid(chargerDetails.getChargerUdid());
            chargerResponse.setQrData(chargerDetails.getQrData());
            chargerResponse.setType(chargerDetails.getType());
            chargerResponse.setLatitude(chargerDetails.getLatitude());
            chargerResponse.setLongitude(chargerDetails.getLongitude());

            if(chargerDetails.getChargerPricingDetails()!=null) {
                ChargerPriceResponse chargerPrice = new ChargerPriceResponse();
                chargerPrice.setChargerUdid(chargerDetails.getChargerPricingDetails().getChargerUdid());
                chargerPrice.setPricingUdid(chargerDetails.getChargerPricingDetails().getPricingUdid());
                chargerPrice.setRatePerHour(chargerDetails.getChargerPricingDetails().getRatePerHr());
                chargerPrice.setRatePerMinute(chargerDetails.getChargerPricingDetails().getRatePerMin());
                chargerPrice.setId(chargerDetails.getChargerPricingDetails().getId());
                chargerResponse.setPrice(chargerPrice);
            }

            return ResponseUtility.createSuccessfulResponse("Chager Found", chargerResponse, HttpStatus.OK);
        }
        else{
            return ResponseUtility.createSuccessfulResponse("Chager NOT Found", "Charger with UDID : " + udid + " Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<APIResponse> getChargerById(@PathVariable("id") Long id){

        Optional<ChargerDetails> chargerDetailsOptional = chargerDetailsRepository.findById(id);
        ChargerDetails chargerDetails = chargerDetailsOptional.isPresent() ? chargerDetailsOptional.get() : null;

        if(Objects.nonNull(chargerDetails)) {
            ChargerResponse chargerResponse = new ChargerResponse();
            chargerResponse.setId(chargerDetails.getId());
            chargerResponse.setChargerName(chargerDetails.getChargerName());
            chargerResponse.setUdid(chargerDetails.getChargerUdid());
            chargerResponse.setQrData(chargerDetails.getQrData());
            chargerResponse.setType(chargerDetails.getType());
            chargerResponse.setLatitude(chargerDetails.getLatitude());
            chargerResponse.setLongitude(chargerDetails.getLongitude());

            if(chargerDetails.getChargerPricingDetails()!=null) {
                ChargerPriceResponse chargerPrice = new ChargerPriceResponse();
                chargerPrice.setChargerUdid(chargerDetails.getChargerPricingDetails().getChargerUdid());
                chargerPrice.setPricingUdid(chargerDetails.getChargerPricingDetails().getPricingUdid());
                chargerPrice.setRatePerHour(chargerDetails.getChargerPricingDetails().getRatePerHr());
                chargerPrice.setRatePerMinute(chargerDetails.getChargerPricingDetails().getRatePerMin());
                chargerPrice.setId(chargerDetails.getChargerPricingDetails().getId());
                chargerResponse.setPrice(chargerPrice);
            }

            return ResponseUtility.createSuccessfulResponse("Chager Found", chargerResponse, HttpStatus.OK);
        }
        else{
            return ResponseUtility.createSuccessfulResponse("Chager NOT Found", "Charger with ID : " + id + " Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findbyname/{chargername}")
    public ResponseEntity<APIResponse> getChargerByChargerName(@PathVariable("chargername") String chargerName){
        List<ChargerDetails> allChargers = chargerDetailsRepository.findByChargerNameContains(chargerName);

        if(Objects.nonNull(allChargers) && allChargers.size()!=0) {
            List<ChargerResponse> chargerResponses = new ArrayList<>();

            for(ChargerDetails chargerDetails : allChargers){
                ChargerResponse chargerResponse = new ChargerResponse();
                chargerResponse.setId(chargerDetails.getId());
                chargerResponse.setChargerName(chargerDetails.getChargerName());
                chargerResponse.setUdid(chargerDetails.getChargerUdid());
                chargerResponse.setQrData(chargerDetails.getQrData());
                chargerResponse.setType(chargerDetails.getType());
                chargerResponse.setLatitude(chargerDetails.getLatitude());
                chargerResponse.setLongitude(chargerDetails.getLongitude());

                if(chargerDetails.getChargerPricingDetails()!=null) {
                    ChargerPriceResponse chargerPrice = new ChargerPriceResponse();
                    chargerPrice.setChargerUdid(chargerDetails.getChargerPricingDetails().getChargerUdid());
                    chargerPrice.setPricingUdid(chargerDetails.getChargerPricingDetails().getPricingUdid());
                    chargerPrice.setRatePerHour(chargerDetails.getChargerPricingDetails().getRatePerHr());
                    chargerPrice.setRatePerMinute(chargerDetails.getChargerPricingDetails().getRatePerMin());
                    chargerPrice.setId(chargerDetails.getChargerPricingDetails().getId());
                    chargerResponse.setPrice(chargerPrice);
                }
                chargerResponses.add(chargerResponse);
            }
            return ResponseUtility.createSuccessfulResponse("Chager Found", chargerResponses, HttpStatus.OK);
        }
        else{
            return ResponseUtility.createSuccessfulResponse("Chager NOT Found", "Charger with name : " + chargerName + " Not Found", HttpStatus.NOT_FOUND);
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
            chargerResponse.setLatitude(charger.getLatitude());
            chargerResponse.setLongitude(charger.getLongitude());

            if(charger.getChargerPricingDetails()!=null) {
                ChargerPriceResponse chargerPrice = new ChargerPriceResponse();
                chargerPrice.setId(charger.getChargerPricingDetails().getId());
                chargerPrice.setChargerUdid(charger.getChargerPricingDetails().getChargerUdid());
                chargerPrice.setRatePerMinute(charger.getChargerPricingDetails().getRatePerMin());
                chargerPrice.setRatePerHour(charger.getChargerPricingDetails().getRatePerHr());
                chargerPrice.setPricingUdid(charger.getChargerPricingDetails().getPricingUdid());
                chargerResponse.setPrice(chargerPrice);
            }

            responses.add(chargerResponse);
        }
        return ResponseUtility.createSuccessfulResponse("All Details", chargerDetails, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> addCharger(@Valid @RequestBody ChargerRequest chargerRequest){
        try {
            String udid = chargerRequest.getChargerName();
            ChargerDetails chargerDetails = new ChargerDetails();
            chargerDetails.setQrData(chargerRequest.getQrData());
            chargerDetails.setType(chargerRequest.getType());
            chargerDetails.setChargerName(udid);
            chargerDetails.setChargerUdid(AppUtilities.generateUDID());
            chargerDetails.setLatitude(chargerRequest.getLatitude());
            chargerDetails.setLongitude(chargerRequest.getLongitude());
            ChargerDetails savedCharger = chargerDetailsRepository.save(chargerDetails);

            //Fetch the charger details;
            ChargerResponse chargerResponse = new ChargerResponse();
            chargerResponse.setId(savedCharger.getId());
            chargerResponse.setChargerName(savedCharger.getChargerName());
            chargerResponse.setUdid(savedCharger.getChargerUdid());
            chargerResponse.setType(savedCharger.getType());
            chargerResponse.setQrData(savedCharger.getQrData());
            chargerResponse.setLatitude(savedCharger.getLatitude());
            chargerResponse.setLongitude(savedCharger.getLongitude());

            return ResponseUtility.createSuccessfulResponse("Charger Data Added", chargerResponse, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseUtility.createFailureResponse("Cannot add charger data", new ArrayList<String>(){{
                add(e.getMessage());
            }}, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removebyudid/{udid}")
    public ResponseEntity<APIResponse> removeUsingUDID(@PathVariable(name = "udid")String udid){
        ChargerDetails chargerDetails = chargerDetailsRepository.findByChargerUdid(udid);
        if(Objects.nonNull(chargerDetails)){
            chargerDetailsRepository.delete(chargerDetails);
            return ResponseUtility.createSuccessfulResponse("Charger with UDID : " + udid + " deleted", "Successful Deletion", HttpStatus.OK);
        }
        else{
            return ResponseUtility.createFailureResponse("Charger NOT Found", new ArrayList<String>(){{
                add("Charger with UDID : " + udid + " Not Found");
            }}, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removebyid/{id}")
    public ResponseEntity<APIResponse> removeUsingID(@PathVariable(name = "id")Long id){
        Optional<ChargerDetails> chargerDetailsOptional = chargerDetailsRepository.findById(id);
        ChargerDetails chargerDetails = chargerDetailsOptional.isPresent() ? chargerDetailsOptional.get() : null;
        if(Objects.nonNull(chargerDetails)){
            chargerDetailsRepository.deleteById(id);
            return ResponseUtility.createSuccessfulResponse("Charger with ID : " + id + " deleted", "Successful Deletion", HttpStatus.OK);
        }
        else{
            return ResponseUtility.createFailureResponse("Charger NOT Found", new ArrayList<String>(){{
                add("Charger with ID : " + id + " Not Found");
            }}, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{udid}")
    public ResponseEntity<APIResponse> updateCharger(@PathVariable(name = "udid") String udid, @RequestBody ChargerRequest chargerRequest){
        ChargerDetails chargerDetails = chargerDetailsRepository.findByChargerUdid(udid);
        if(Objects.nonNull(chargerDetails)){

            ChargerDetails updateData = new ChargerDetails();
            BeanUtils.copyProperties(chargerRequest, updateData);
            updateData.setChargerUdid(chargerDetails.getChargerUdid());
            updateData.setId(chargerDetails.getId());

            ChargerDetails updatedChargerDetails = chargerDetailsRepository.save(updateData);
            return ResponseUtility.createSuccessfulResponse("Charger details updated for UDID : "+ udid + " updated", updatedChargerDetails,
                    HttpStatus.OK);
        }
        else{
            return ResponseUtility.createFailureResponse("Chager NOT Found", new ArrayList<String>(){{
                add("Charger with UDID : " + udid + " Not Found");
            }}, HttpStatus.NOT_FOUND);
        }
    }

}
