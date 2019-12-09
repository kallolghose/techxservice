package com.techx.dbhandler.models.inventoryservice;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargerdetails")
@Getter
@Setter
public class ChargerDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chargerudid")
    private String chargerUdid;

    @Column(name = "qrdata")
    private String qrData;

    @Column(name = "type")
    private String type;

    @Column(name = "chargername")
    private String chargerName;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chargerudid",
            referencedColumnName = "chargerudid",
            insertable = false,
            updatable = false
    )
    private ChargerPricingDetails chargerPricingDetails;

}
