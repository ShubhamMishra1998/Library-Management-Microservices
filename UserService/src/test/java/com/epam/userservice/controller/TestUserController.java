package com.epam.userservice.controller;

import com.epam.userservice.dto.UserDTO;
import com.epam.userservice.entities.User;
import com.epam.userservice.exceptions.DuplicateUserNameException;
import com.epam.userservice.exceptions.ResourceNotFoundException;
import com.epam.userservice.service.UserServiceImpl;
import com.epam.userservice.util.TestUtils;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
  class TestUserController {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userService;
    UserDTO userDto;
    User user;

    @BeforeEach
    public void setUp() {
        this.userDto = TestUtils.createUserDto();
        this.user = TestUtils.createUser();
    }

    @Test
    void testAddUser() throws Exception {

        when(userService.add(userDto)).thenReturn(userDto);
        Gson gson = new Gson();
        String userJson = gson.toJson(userDto);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated());

    }

    @Test
    void testAddUserDuplicateUserName() throws Exception {
        userDto.hashCode();

        when(userService.add(userDto)).thenThrow(DuplicateUserNameException.class);
        Gson gson = new Gson();
        String userJson = gson.toJson(userDto);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isConflict());

    }

    @Test
    void testGetUser() throws Exception {
        when(userService.get(Mockito.anyString())).thenReturn(userDto);
        mockMvc.perform(get("/users/Shiv123")).andExpect(status().isOk());
    }

    @Test
    void testGetNonExistingUser() throws Exception {
        Mockito.when(userService.get(Mockito.anyString())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/users/Shiv123")).andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).delete(Mockito.anyString());
        mockMvc.perform(delete("/users/Shiv123")).andExpect(status().isNoContent());

    }

    @Test
    void testUpdateUser() throws Exception {
        Mockito.when(userService.get(Mockito.anyString())).thenReturn(userDto);
        Gson gson = new Gson();
        String userJson = gson.toJson(userDto);
        mockMvc.perform(put("/users/shiv").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());

    }

    @Test
    void testUpdateUserMissingArguments() throws Exception {
        Mockito.when(userService.get(Mockito.anyString())).thenReturn(userDto);
        Gson gson = new Gson();
        userDto.setEmail(null);
        String userJson = gson.toJson(userDto);
        mockMvc.perform(put("/users/shiv").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGetAll() throws Exception {
        List<UserDTO> userDtos = new ArrayList<>();
        userDtos.add(userDto);
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }



}
