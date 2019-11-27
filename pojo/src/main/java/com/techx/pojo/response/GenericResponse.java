package com.techx.pojo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GenericResponse<T> {

    private Boolean status;
    private String message;
    private List<String> error;
    private T data;

}
