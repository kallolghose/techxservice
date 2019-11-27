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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "creationdate")
    private Date creationDate;

    @Column(name = "isdcode")
    private String isdCode;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "otp")
    private String otp;
}
