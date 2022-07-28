package com.epam.libraryservice.clients;

import com.epam.libraryservice.dto.UserDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "user-service/users",fallback = UserClientImpl.class)
@LoadBalancerClient(value = "user-service",configuration = UserClientImpl.class)
public interface UserClient {

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers();

    @GetMapping("/{userName}")
    ResponseEntity<UserDto> getAUser(@PathVariable @Valid String userName);

    @PostMapping
    ResponseEntity<UserDto> addAUser(@RequestBody  @Valid UserDto userDto);

    @PutMapping("/{userName}")
    ResponseEntity<UserDto> updateAUser(@PathVariable @Valid String userName, @RequestBody @Valid UserDto userDto);

    @DeleteMapping("/{userName}")
    ResponseEntity<UserDto> deleteAUser(@PathVariable @Valid String userName);
}
