package com.techx.pojo.response.inventory.price;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChargerPriceResponse {

    private Long id;
    private String pricingUdid;
    private String chargerUdid;
    private Float ratePerHour;
    private Float ratePerMinute;

}
