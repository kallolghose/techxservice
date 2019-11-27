package com.techx.userservice.controller.signup;

import com.techx.pojo.request.user.signup.SignUpRequest;
import com.techx.pojo.response.user.signup.SignUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    @PostMapping("/create")
    public ResponseEntity<SignUpResponse> createUser(@Valid @RequestBody(required = false) SignUpRequest signUpRequest){
        //Add validations for the following
        //1. Email ID
        //2. Phone Number
        //3. Password
        List<String> errors = new ArrayList<>();


        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private List<String> validateRequest(SignUpRequest signUpRequest){
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        List<String> errors = new ArrayList<>();
        if(signUpRequest.getEmailId()!=null){
            
        }
        else {
            errors.add("Email Id cannot be empty");
        }

        if (signUpRequest.getPassword()!=null){
            //Check the length of password
        }
        else {
            errors.add("Password cannot be empty");
        }

        if(signUpRequest.getPhoneNo()==null || signUpRequest.getIsdCode()==null){
            errors.add("Phone number/ISD Code cannot be empty");
        }

        return errors;
    }

}
