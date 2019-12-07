package com.techx.pojo.response.inventory.price;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChargerPrice {

    private Long id;
    private String pricingUdid;
    private String chargerUdid;
    private String ratePerHour;
    private String ratePerMinute;

}
