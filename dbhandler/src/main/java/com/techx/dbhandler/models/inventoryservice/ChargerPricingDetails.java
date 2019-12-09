package com.techx.dbhandler.models.inventoryservice;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargerpricingdetails")
@Getter
@Setter
@Transactional
public class ChargerPricingDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chargerudid")
    private String chargerUdid;

    @Column(name = "pricingudid")
    private String pricingUdid;

    @Column(name = "rateperhr")
    private Float ratePerHr;

    @Column(name = "ratepermin")
    private Float ratePerMin;
}
