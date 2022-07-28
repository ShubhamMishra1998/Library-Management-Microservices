package com.epam.userservice.service;

import com.epam.userservice.dto.UserDTO;

import java.util.List;

public interface UserService {

     UserDTO add(UserDTO userDto);
     boolean isUserNameAlreadyExist(String userName);
     void delete(String userName);
     void update(UserDTO userDto);
     UserDTO get(String userName);
     List<UserDTO> getAll();

}
