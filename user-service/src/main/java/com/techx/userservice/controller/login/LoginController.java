package com.techx.userservice.controller.login;

import com.techx.dbhandler.models.userservice.UserDetails;
import com.techx.dbhandler.repository.userservice.UserDetailsRepository;
import com.techx.pojo.request.user.login.LoginRequest;
import com.techx.pojo.response.APIResponse;
import com.techx.pojo.response.user.login.LoginResponse;
import com.techx.utilities.AppUtilities;
import com.techx.utilities.ResponseUtility;
import com.techx.utilities.constants.Constants;
import com.techx.utilities.constants.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public LoginController(UserDetailsRepository userDetailsRepository){
        this.userDetailsRepository = userDetailsRepository;
    }

    @PostMapping("/validate")
    public ResponseEntity<APIResponse> validate(@Valid @RequestBody(required = false) LoginRequest loginRequest){
        //Currently doing the same for Phone Number only
        //Later will extent the same to email id
        //Add active flag check
        UserDetails userDetails = userDetailsRepository.findByPhoneNumber("" + loginRequest.getPhoneNo());
        if(Objects.nonNull(userDetails)){
            if(!userDetails.getActive().equals("N")) {
                byte[] salt = userDetails.getSalt();
                String generateSecurePassword = AppUtilities.getSecurePhrase(loginRequest.getPassword(), salt);
                if (generateSecurePassword.equals(userDetails.getPassword())) {
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setFirstName("Hello");
                    loginResponse.setLastName("There");
                    loginResponse.setEmailId(userDetails.getEmailId());
                    loginResponse.setPhoneNo(Long.parseLong(userDetails.getPhoneNumber()));
                    loginResponse.setIsdCode(userDetails.getIsdCode());
                    loginResponse.setToken("Any Token");
                    loginResponse.setUserid(userDetails.getUserId());
                    return ResponseUtility.createSuccessfulResponse(Messages.SUCCESS.getMessage(), loginResponse, HttpStatus.OK);
                } else {
                    return ResponseUtility.createFailureResponse(Messages.AUTHENTICATION_FAILURE.getMessage(), new ArrayList<String>() {{
                        add("Password does not match");
                    }}, HttpStatus.UNAUTHORIZED);
                }
            }
            else{
                String status = userDetails.getStatus();
                return ResponseUtility.createFailureResponse(Messages.ADDITION_VERIFICATION.getMessage(), new ArrayList<String>(){{
                    add("User is not active");
                    add(Constants.valueOf(status).getStatus());
                }}, HttpStatus.OK);
            }
        }
        else{
            return ResponseUtility.createFailureResponse(Messages.USER_DOES_NOT_EXISTS.getMessage(), new ArrayList(){{
                add(loginRequest.getPhoneNo() + " is not registered with us. Please register.");
            }}, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/checkAuthorization")
    public String authorizationCheck(){
        return "Some Text";
    }
}
