package com.epam.libraryservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
public class BookDto {

	private int id;
	@NotBlank(message = "Book name must not be empty")
	private String name;
	@NotBlank(message = "Book publisher must not be empty")
	private String publisher;
	@NotBlank(message = "Book author must not be empty")
	private String author;
}
