package com.techx.dbhandler.models.inventoryservice;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargingstationdetails")
@Getter
@Setter
@Transactional
public class ChargingStationDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stationudid")
    private String stationUdid;

    @Column(name = "stationame")
    private String stationName;

    @Column(name = "latitute")
    private Float latitute;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "pincode")
    private Long pincode;

    @OneToMany
    @JoinTable(
            name = "chargingstation_chargermapping",
            joinColumns = {@JoinColumn(name = "stationudid")},
            inverseJoinColumns = {@JoinColumn(name = "chargerudid", insertable = false, updatable = false)}
    )
    private List<ChargerDetails> chargerDetailsList;

}
