package com.techx.pojo.request.inventory.charger;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class ChargerRequest {

    @NotNull(message = "Please enter QR Data")
    private String qrData;

    @NotNull(message = "Please enter type")
    private String type;

    @NotNull(message = "Please enter charger name")
    private String chargerName;

    @NotNull(message = "Please enter latitude")
    private Double latitude;

    @NotNull(message = "Please enter longitude")
    private Double longitude;

}
