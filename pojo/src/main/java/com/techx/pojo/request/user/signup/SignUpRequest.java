package com.techx.pojo.request.user.signup;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class SignUpRequest {

    private String emailId;
    private String phoneNo;
    private String isdCode;
    private Date dateOfBirth;
    private Integer age;
    private String gender;
    private String password;

}
