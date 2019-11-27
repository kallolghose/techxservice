package com.techx.pojo.request.user.login;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    private String emailId;
    private String isdCode;
    private Long phoneNo;
    private String password;

}
