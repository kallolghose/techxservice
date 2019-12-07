package com.techx.dbhandler.models.inventoryservice;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargerpricingdetails")
@Getter
@Setter
public class ChargerPricingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pricingudid")
    private String pricingUdid;

    @Column(name = "chargerudid")
    private String chargerUdid;

    @Column(name = "rateperhr")
    private String ratePerHr;

    @Column(name = "ratepermin")
    private String ratePerMin;
}
