package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;


public interface UserService extends UserDetailsService {
    boolean saveOrUpdateUser(User user);

    boolean deleteUserById(Long id);

    User findUserByID(Long id);

    Set<User> findAllUsers();

    User findByUsername(String login);

}
