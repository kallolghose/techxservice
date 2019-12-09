package com.techx.pojo.request.user.signup;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class SignUpRequest {

    private String emailId;
    private Long phoneNo;
    private String isdCode;
    private String password;

}
