package com.techx.pojo.response.user.otp;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OtpResponse {

    private String userId;
    private String emailId;
    private Long phoneNo;
    private String isdCode;

}
