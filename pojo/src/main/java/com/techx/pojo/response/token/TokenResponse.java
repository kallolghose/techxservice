package com.techx.pojo.response.token;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenResponse {

    private Boolean status;
    private String authentication;
    private String token;

}
