package com.techx.utilities.constants;

public enum Constants {

    OTP_VALIDATION_REQUIRED("OTP Validation Required."),
    OTP_VALIDATION_COMPLETED("Phone number verified.");

    private String status;
    public String getStatus(){
        return this.status;
    }
    Constants(String status){
        this.status = status;
    }
}
