package com.epam.userservice.controllers;

import com.epam.userservice.dto.UserDTO;
import com.epam.userservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    UserServiceImpl userService;


    @PostMapping
    public ResponseEntity<UserDTO> add(@RequestBody @Valid UserDTO userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(userDto));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDTO> get(@PathVariable("userName") String userName) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.get(userName));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<UserDTO> delete(@PathVariable("userName") String userName) {
        userService.delete(userName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO updatedUser,
                                          @PathVariable("userName") String userName) {

        UserDTO originalUser = userService.get(userName);
        updatedUser.setId(originalUser.getId());
        updatedUser.setUserName(userName);


        userService.update(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }





}
