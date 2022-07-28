package com.epam.userservice.service;

import com.epam.userservice.dto.UserDTO;
import com.epam.userservice.entities.User;
import com.epam.userservice.exceptions.DuplicateUserNameException;
import com.epam.userservice.exceptions.ResourceNotFoundException;
import com.epam.userservice.repository.UserRepository;
import com.epam.userservice.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
 class TestUserService {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    ModelMapper modelMapper;
    UserDTO userDto;
    User user;

    @BeforeEach
    public void setUp() {
        this.userDto = TestUtils.createUserDto();
        this.user = TestUtils.createUser();

    }

    @Test
    void testAddUser() {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUserName());
        userDto.setName(user.getName());

        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDto);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDTO actual = userService.add(userDto);
        Assertions.assertEquals(userDto.getName(), actual.getName());

    }

    @Test
    void testAddUserDuplicateUserName() {

        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));

        Assertions.assertThrows(DuplicateUserNameException.class, () -> userService.add(userDto));

    }

    @Test
    void testGetUser() {
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserDTO.class)).thenReturn(userDto);
        UserDTO actual = userService.get("Shiv");
        Assertions.assertEquals(userDto.getName(), actual.getName());

    }

    @Test
    void testGetNonExistingUser() {
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.get("Shiv"));

    }

    @Test
    void testDeleteUser() {
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDTO.class)).thenReturn(userDto);
        userService.delete("Shiv");
        Mockito.verify(userRepository).delete(Mockito.any());
    }

    @Test
    void testUpdateUser() {
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.update(userDto);
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> userDtos = new ArrayList<>();
        List<User> users = new ArrayList<User>();
        users.add(user);
        userDtos.add(userDto);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(modelMapper.map(users, new TypeToken<List<UserDTO>>() {
        }.getType())).thenReturn(userDtos);
        List<UserDTO> actual = userService.getAll();
        Assertions.assertEquals(userDtos.size(), actual.size());

    }




}
