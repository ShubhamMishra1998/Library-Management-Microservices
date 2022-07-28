package com.epam.userservice.repository;

import com.epam.userservice.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
     Optional<User> findByUserName(String userName);
}
