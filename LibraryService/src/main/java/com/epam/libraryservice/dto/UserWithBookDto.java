package com.epam.libraryservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithBookDto {
	private long id;
	@NotBlank(message = "userName must not be blank")
	private String userName;
	@NotBlank(message = "email must not be blank")

	private String email;
	@NotBlank(message = "name must not be blank")
	private String name;

	private List<BookDto> books;

}
