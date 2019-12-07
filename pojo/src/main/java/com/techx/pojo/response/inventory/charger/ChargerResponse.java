package com.techx.pojo.response.inventory.charger;

import com.techx.pojo.response.inventory.price.ChargerPrice;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChargerResponse {

    private Long id;
    private String udid;
    private String qrData;
    private String type;
    private String chargerName;
    private ChargerPrice price;

}
