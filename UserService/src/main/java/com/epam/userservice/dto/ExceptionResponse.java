package com.epam.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private String timeStamp;
    private String status;
    private String developerMessage;
    private String path;

}
