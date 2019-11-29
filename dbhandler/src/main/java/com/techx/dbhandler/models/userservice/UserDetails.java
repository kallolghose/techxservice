package com.techx.dbhandler.models.userservice;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userdetails")
@Getter
@Setter
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private String userId;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "isdcode")
    private String isdCode;

    @Column(name = "emailid")
    private String emailId;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private byte[] salt;

    @Column(name = "active")
    private String active;

    @Column(name = "status")
    private String status;
}
