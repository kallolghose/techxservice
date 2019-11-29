package com.techx.userservice.controller.signup;

import com.techx.dbhandler.models.userservice.Otp;
import com.techx.dbhandler.models.userservice.UserDetails;
import com.techx.dbhandler.repository.userservice.OTPRepository;
import com.techx.dbhandler.repository.userservice.UserDetailsRepository;
import com.techx.pojo.request.user.signup.SignUpRequest;
import com.techx.pojo.response.APIResponse;
import com.techx.pojo.response.user.signup.SignUpResponse;
import com.techx.utilities.AppUtilities;
import com.techx.utilities.constants.Constants;
import com.techx.utilities.constants.Messages;
import com.techx.utilities.ResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private OTPRepository otpRepository;

    public SignUpController(UserDetailsRepository userDetailsRepository, OTPRepository otpRepository){
        this.userDetailsRepository = userDetailsRepository;
        this.otpRepository = otpRepository;
    }


    @PostMapping("/create")
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody(required = false) SignUpRequest signUpRequest){
        List<String> errors = validateRequest(signUpRequest);
        if(errors.size()!=0){
            return ResponseUtility.createFailureResponse(Messages.BAD_REQUEST.getMessage(),errors,HttpStatus.BAD_REQUEST);
        }
        else{
            UserDetails existingUserDetails = userDetailsRepository.findByPhoneNumber(signUpRequest.getPhoneNo() + "");
            if(Objects.isNull(existingUserDetails)) {
                String userID = UUID.randomUUID().toString();
                UserDetails userDetails = new UserDetails();
                userDetails.setEmailId(signUpRequest.getEmailId());
                userDetails.setPhoneNumber("" + signUpRequest.getPhoneNo());
                userDetails.setIsdCode(signUpRequest.getIsdCode());
                userDetails.setActive("N");
                userDetails.setUserId(userID);
                userDetails.setStatus(Constants.OTP_VALIDATION.name());

                //Get Salt and encrypted password
                byte[] salt = AppUtilities.getSalt();
                String securedPassword = AppUtilities.getSecurePhrase(signUpRequest.getPassword(), salt);

                userDetails.setPassword(securedPassword);
                userDetails.setSalt(salt);
                userDetailsRepository.save(userDetails);

                int otpGenerated = generateOTP();
                Otp otp = new Otp();
                otp.setCreationDate(new Date());
                otp.setIsdCode(signUpRequest.getIsdCode());
                otp.setPhoneNumber("" + signUpRequest.getPhoneNo());
                otp.setOtp(otpGenerated);
                otp.setUserid(userID);
                otpRepository.save(otp);

                SignUpResponse signUpResponse = new SignUpResponse();
                signUpResponse.setEmailId(signUpRequest.getEmailId());
                signUpResponse.setPhoneNo(signUpRequest.getPhoneNo());
                signUpResponse.setIsdCode(signUpRequest.getIsdCode());
                signUpResponse.setStatus(Constants.OTP_VALIDATION.name());
                signUpResponse.setActive("N");
                signUpResponse.setUserId(userID);

                return ResponseUtility.createSuccessfulResponse(Messages.SUCCESS.getMessage(), signUpResponse, HttpStatus.OK);
            }
            else {
                return ResponseUtility.createFailureResponse(Messages.USER_EXISTS.getMessage(),new ArrayList(){{
                    add("User with " + signUpRequest.getPhoneNo() + " already registered");
                }},HttpStatus.CONFLICT);
            }
        }
    }


    /**
     * Function to generate OTP
     * @return
     */
    private int generateOTP(){
        Random random = new Random();
        int n = 100000 + (int)(random.nextFloat() * 100000);
        logger.info("OTP Generated : " + n);
        return n;
    }

    /**
     * Function to validate the values of the request
     * Validation performed
     * 1. Validate email id
     * 2. Validate Phone Number
     * 3. Validate Password
     * @param signUpRequest
     * @return
     */
    private List<String> validateRequest(SignUpRequest signUpRequest){
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        List<String> errors = new ArrayList<>();
        if(signUpRequest.getEmailId()!=null && !signUpRequest.getEmailId().equals("")){
            Pattern emailPattern = Pattern.compile(emailRegex);
            if(!emailPattern.matcher(signUpRequest.getEmailId()).matches()){
                errors.add("Email ID is invalid");
            }
        }
        else {
            errors.add("Email Id cannot be empty");
        }
        if (signUpRequest.getPassword()==null || signUpRequest.getPassword().equals("")){
            errors.add("Password cannot be empty");
        }
        else {

        }

        if((signUpRequest.getPhoneNo()==null || signUpRequest.getPhoneNo().equals("")) ||
                (signUpRequest.getIsdCode()==null || signUpRequest.getIsdCode().equals(""))) {
            errors.add("Phone number/ISD Code cannot be empty");
        }
        else{
            if(signUpRequest.getPhoneNo() < 1000000000L){
                errors.add("Phone number is invalid");
            }
        }
        return errors;
    }

}
