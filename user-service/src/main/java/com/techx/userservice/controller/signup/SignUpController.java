package com.techx.userservice.controller.signup;

import com.netflix.ribbon.proxy.annotation.Http;
import com.techx.dbhandler.models.userservice.Otp;
import com.techx.dbhandler.models.userservice.Roles;
import com.techx.dbhandler.models.userservice.UserDetails;
import com.techx.dbhandler.models.userservice.UserRoles;
import com.techx.dbhandler.repository.userservice.OTPRepository;
import com.techx.dbhandler.repository.userservice.RolesRepository;
import com.techx.dbhandler.repository.userservice.UserDetailsRepository;
import com.techx.dbhandler.repository.userservice.UserRolesRepository;
import com.techx.pojo.request.user.otp.OtpRequest;
import com.techx.pojo.request.user.signup.SignUpRequest;
import com.techx.pojo.response.APIResponse;
import com.techx.pojo.response.user.otp.OtpResponse;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private RolesRepository rolesRepository;


    public SignUpController(UserDetailsRepository userDetailsRepository, OTPRepository otpRepository,
                            UserRolesRepository userRolesRepository, RolesRepository rolesRepository){
        this.userDetailsRepository = userDetailsRepository;
        this.otpRepository = otpRepository;
        this.userRolesRepository = userRolesRepository;
        this.rolesRepository = rolesRepository;
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
                userDetails.setStatus(Constants.OTP_VALIDATION_REQUIRED.name());

                //Get Salt and encrypted password
                byte[] salt = AppUtilities.getSalt();
                String securedPassword = AppUtilities.getSecurePhrase(signUpRequest.getPassword(), salt);

                userDetails.setPassword(encoder.encode(signUpRequest.getPassword()));
                userDetails.setSalt(salt);
                userDetailsRepository.save(userDetails);

                UserRoles userRoles = new UserRoles();
                userRoles.setUserId(userID);
                userRoles.setRoleId(rolesRepository.findByRole("USER").getRoleId());
                userRolesRepository.save(userRoles);

                OtpRequest otpRequest = new OtpRequest();
                otpRequest.setEmailId(signUpRequest.getEmailId());
                otpRequest.setIsdCode(signUpRequest.getIsdCode());
                otpRequest.setPhoneNo(signUpRequest.getPhoneNo());
                otpGeneration(otpRequest);

                SignUpResponse signUpResponse = new SignUpResponse();
                signUpResponse.setEmailId(signUpRequest.getEmailId());
                signUpResponse.setPhoneNo(signUpRequest.getPhoneNo());
                signUpResponse.setIsdCode(signUpRequest.getIsdCode());
                signUpResponse.setStatus(Constants.OTP_VALIDATION_REQUIRED.name());
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

    @PostMapping("/generateOtp")
    public ResponseEntity<APIResponse> otpGeneration(@Valid @RequestBody(required = false)OtpRequest otpRequest){

        UserDetails userDetails = userDetailsRepository.findByPhoneNumber("" + otpRequest.getPhoneNo());
        if(Objects.nonNull(userDetails)) {
            long otpGenerated = generateOTP();
            Otp otp = new Otp();
            otp.setCreationDate(new Date());
            otp.setIsdCode(otpRequest.getIsdCode());
            otp.setPhoneNumber("" + otpRequest.getPhoneNo());
            otp.setOtp(otpGenerated);
            otp.setUserid(userDetails.getUserId());
            otp.setExpired("N");
            otpRepository.save(otp);

            OtpResponse otpResponse = new OtpResponse();
            otpResponse.setEmailId(userDetails.getEmailId());
            otpResponse.setIsdCode(otpRequest.getIsdCode());
            otpResponse.setPhoneNo(otpRequest.getPhoneNo());
            otpResponse.setUserId(userDetails.getUserId());
            otpResponse.setMessage("OTP Generated");

            return ResponseUtility.createSuccessfulResponse(Messages.SUCCESS.getMessage(), otpResponse, HttpStatus.OK);
        }
        else {
            return ResponseUtility.createFailureResponse(Messages.USER_DOES_NOT_EXISTS.getMessage(), new ArrayList<String>(){{
                add(otpRequest.getPhoneNo() + " is not register with us. Please register");
            }}, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/validateotp")
    public ResponseEntity<APIResponse> validateOTP(@Valid @RequestBody(required = false) OtpRequest otpRequest){

        List<Otp> otps = otpRepository.findByPhoneNumberAndExpired("" + otpRequest.getPhoneNo(), "N");
        logger.info("OTP SIZE : " + otps.size() + "");
        if(otps.size()>0){
            logger.info("OTP in table : " + otps.get(0).getOtp() + " Request : " + otpRequest.getOtp());
            if(otps.get(0).getOtp().longValue() == otpRequest.getOtp().longValue()){
                int updateStatus = otpRepository.updateOTPSetExpiredForId("Y", otps.get(0).getId());
                logger.info("Update Status of OTP[Expired] : " + updateStatus);

                updateStatus = userDetailsRepository.updateUserDetailsSetActiveForUserid(otps.get(0).getUserid(), "Y");
                logger.info("Update Status od UserDetails[Active] : " + updateStatus);

                updateStatus = userDetailsRepository.updateUserDetailsSetStatusForUserid(otps.get(0).getUserid(), Constants.OTP_VALIDATION_COMPLETED.name());
                logger.info("Update Status od UserDetails[Status] : " + updateStatus);

                return ResponseUtility.createSuccessfulResponse(Messages.SUCCESS.getMessage(), null, HttpStatus.OK);
            }
            else{
                return ResponseUtility.createFailureResponse(Messages.INVALID_OTP.getMessage(), new ArrayList<String>(){{
                    add(Messages.INVALID_OTP.getMessage());
                }}, HttpStatus.OK);
            }
        }
        else{
            return ResponseUtility.createFailureResponse(Messages.INVALID_OTP.getMessage(), new ArrayList<String>(){{
                add(Messages.INVALID_OTP.getMessage());
            }}, HttpStatus.OK);
        }
    }


    /**
     * Function to generate OTP
     * @return
     */
    private long generateOTP(){
        Random random = new Random();
        long n = 100000 + (long)(random.nextFloat() * 100000);
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
