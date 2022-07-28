package com.epam.libraryservice.clients;

import com.epam.libraryservice.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserClientImpl implements  UserClient{

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserDto> getAUser(String userName) {
        return new ResponseEntity<>(new UserDto(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserDto> addAUser(UserDto userDto) {
        return new ResponseEntity<>(new UserDto(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserDto> updateAUser(String userName, UserDto userDto) {
        return new ResponseEntity<>(new UserDto(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserDto> deleteAUser(String userName) {
        return new ResponseEntity<>(new UserDto(), HttpStatus.NO_CONTENT);
    }

}
