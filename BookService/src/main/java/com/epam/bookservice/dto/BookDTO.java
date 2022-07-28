package com.epam.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BookDTO {
    private int id;
    @NotBlank(message = "Book name must not be empty")
    private String name;
    @NotBlank(message = "Book publisher must not be empty")
    private String publisher;
    @NotBlank(message = "Book author must not be empty")
    private String author;

}
