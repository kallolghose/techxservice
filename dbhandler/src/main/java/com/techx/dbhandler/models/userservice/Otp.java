package com.techx.dbhandler.models.userservice;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "otp")
@Getter
@Setter
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creationdate")
    private Date creationDate;

    @Column(name = "isdcode")
    private String isdCode;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "otp")
    private Long otp;

    @Column(name = "userid")
    private String userid;

    @Column(name = "expired")
    private String expired;

}
