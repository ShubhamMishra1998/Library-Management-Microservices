package com.epam.userservice.util;

import com.epam.userservice.dto.UserDTO;
import com.epam.userservice.entities.User;

public class TestUtils {

    public static UserDTO createUserDto() {
        UserDTO userDto = new UserDTO();
        userDto.setId(1);
        userDto.setUserName("Shiv1234");
        userDto.setEmail("Shiv@gmail");
        userDto.setName("Shiv");
        return userDto;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("Shiv1234");
        user.setEmail("Shiv@gmail");
        user.setName("Shiv");
        return user;
    }

}
