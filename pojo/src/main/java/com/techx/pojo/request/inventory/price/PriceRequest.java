package com.techx.pojo.request.inventory.price;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PriceRequest {

    private String charegerUdid;
    private Float ratePerHr;
    private Float getRatePerMin;

}
