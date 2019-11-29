package com.techx.pojo.response.user.login;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {

    private String firstName;
    private String lastName;
    private String userid;
    private String emailId;
    private String isdCode;
    private Long phoneNo;
    private String token;

}
