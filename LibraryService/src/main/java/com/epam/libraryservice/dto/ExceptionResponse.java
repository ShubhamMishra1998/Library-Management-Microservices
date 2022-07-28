package com.epam.libraryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String timeStamp;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String status;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String developerMessage;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String path;

}
