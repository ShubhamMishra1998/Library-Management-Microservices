package com.epam.userservice.service;

import com.epam.userservice.dto.UserDTO;
import com.epam.userservice.entities.User;
import com.epam.userservice.exceptions.DuplicateUserNameException;
import com.epam.userservice.exceptions.ResourceNotFoundException;
import com.epam.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDTO add(UserDTO userDto) {
        if (isUserNameAlreadyExist(userDto.getUserName())) {
            throw new DuplicateUserNameException("UserName already exists, please try another UserName");
        }
        return modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDTO.class);

    }

    @Override
    public boolean isUserNameAlreadyExist(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public void delete(String userName) {
        userRepository.delete(modelMapper.map(get(userName), User.class));
    }

    @Override
    public void update(UserDTO userDto) {
        userRepository.save(modelMapper.map(userDto, User.class));
    }

    @Override
    public UserDTO get(String userName){
        return modelMapper.map(userRepository.findByUserName(userName)
                        .orElseThrow(() -> new ResourceNotFoundException("User name:" + userName + " does not exist")),
                        UserDTO.class);



    }

    @Override
    public List<UserDTO> getAll() {
        return modelMapper.map((List<User>) userRepository.findAll(), new TypeToken<List<UserDTO>>() {

        }.getType());

    }
}
