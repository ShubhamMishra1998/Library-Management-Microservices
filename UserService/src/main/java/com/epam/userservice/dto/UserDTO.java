package com.epam.userservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private long id;
    @NotBlank(message = "userName must not be blank")
    private String userName;
    @NotBlank(message = "email must not be blank")
    @Email(message = "please enter correct email")
    private String email;
    @NotBlank(message = "name must not be blank")
    private String name;

}
