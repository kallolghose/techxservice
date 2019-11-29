package com.techx.utilities.constants;

public enum Constants {

    OTP_VALIDATION("Send for OTP Validation");

    private String status;
    public String getStatus(){
        return this.status;
    }
    Constants(String status){
        this.status = status;
    }
}
