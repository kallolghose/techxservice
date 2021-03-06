package com.techx.utilities.constants;

import lombok.Getter;

public enum  Messages {

    USER_EXISTS("User already registered"),
    USER_DOES_NOT_EXISTS("User not registered"),
    ADDITION_VERIFICATION("Additional Verification Required"),
    BAD_REQUEST("Incorrect data entered"),
    AUTHENTICATION_FAILURE("Authentication Failed"),
    SUCCESS("Successful"),
    INVALID_OTP("OTP entered is invalid");

    private String message;
    public String getMessage(){
        return this.message;
    }
    Messages(String message){
        this.message = message;
    }

}
