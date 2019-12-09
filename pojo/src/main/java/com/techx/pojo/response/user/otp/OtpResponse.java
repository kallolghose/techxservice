package com.techx.pojo.response.user.otp;

import com.techx.pojo.response.token.TokenResponse;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OtpResponse {

    private String userId;
    private String emailId;
    private Long phoneNo;
    private String isdCode;
    private String message;
    private TokenResponse token;

}
