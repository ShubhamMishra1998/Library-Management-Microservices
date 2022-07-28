package com.epam.libraryservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@EqualsAndHashCode
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private long id;
	@NotBlank(message = "userName must not be blank")
	private String userName;
	@NotBlank(message = "email must not be blank")

	private String email;
	@NotBlank(message = "name must not be blank")
	private String name;
}
