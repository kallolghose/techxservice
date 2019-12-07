package com.techx.dbhandler.models.inventoryservice;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chargerdetails")
@Getter
@Setter
public class ChargingStationDetails {

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



}
