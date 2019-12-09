package com.techx.utilities;

import com.techx.pojo.response.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public class ResponseUtility {

    private static Logger logger = LoggerFactory.getLogger(ResponseUtility.class);

    public static ResponseEntity<APIResponse> createSuccessfulResponse(String message, Object responseObject, HttpStatus httpStatus){
        logger.info("Create Successful Response");
        APIResponse response = new APIResponse();
        response.setStatus(true);
        response.setMessage(message);
        if (responseObject!=null)
            response.setData(responseObject);
        return new ResponseEntity<APIResponse>(response, httpStatus);
    }

    /**
     * Function to send successful response
     * @param message
     * @param key
     * @param value
     * @param httpStatus
     * @return
     */
    public static ResponseEntity<APIResponse> createSuccessfulResponse(String message, Object key, Object value, HttpStatus httpStatus){
        logger.info("Create Successful Response");
        APIResponse response = new APIResponse();
        response.setStatus(true);
        response.setMessage(message);
        if(key!=null){
            response.setData(new HashMap<Object, Object>(){
                {
                    put(key, value);
                }
            });
        }
        return new ResponseEntity<APIResponse>(response, httpStatus);
    }

    /**
     * Function to send failure response
     * @param message
     * @param errors
     * @param httpStatus
     * @return
     */
    public static ResponseEntity<APIResponse> createFailureResponse(String message, List errors, HttpStatus httpStatus){
        logger.info("Create Failure Response");
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(false);
        apiResponse.setMessage(message);
        apiResponse.setError(errors);
        return new ResponseEntity<APIResponse>(apiResponse, httpStatus);
    }

    /**
     * Function to send failure response
     * @param message
     * @param errors
     * @param httpStatus
     * @return
     */
    public static ResponseEntity<APIResponse> createFailureResponse(String message, List errors, Object data, HttpStatus httpStatus){
        logger.info("Create Failure Response");
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(false);
        apiResponse.setMessage(message);
        apiResponse.setError(errors);
        apiResponse.setData(data);
        return new ResponseEntity<APIResponse>(apiResponse, httpStatus);
    }
}
