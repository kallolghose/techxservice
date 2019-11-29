package com.techx.pojo.response.user.signup;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpResponse {

    private String userId;
    private String emailId;
    private Long phoneNo;
    private String isdCode;
    private String status;
    private String active;
}
