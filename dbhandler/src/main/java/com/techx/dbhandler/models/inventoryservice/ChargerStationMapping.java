package com.techx.dbhandler.models.inventoryservice;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargingstation_chargermapping")
@Getter
@Setter
public class ChargerStationMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stationudid")
    private String stationUdid;

    @Column(name = "chargerudid")
    private String chargerUdid;

    @Column(name = "active")
    private String active;
}
