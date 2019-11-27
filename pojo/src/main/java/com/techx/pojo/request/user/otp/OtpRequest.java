package com.techx.pojo.request.user.otp;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OtpRequest {

    private String emailId;
    private String isdCode;
    private Long phoneNo;
    private Integer otp;

}
