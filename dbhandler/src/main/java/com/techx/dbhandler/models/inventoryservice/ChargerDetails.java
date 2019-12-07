package com.techx.dbhandler.models.inventoryservice;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargerdetails")
@Getter
@Setter
public class ChargerDetails {

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

    @JoinTable
    @OneToOne
    ChargerPricingDetails chargerPricingDetails;

}
