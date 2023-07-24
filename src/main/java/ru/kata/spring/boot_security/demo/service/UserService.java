package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;


public interface UserService {
    boolean saveOrUpdateUser(User user);

    boolean deleteUser(Long id);

    User getUserById(Long id);

    Set<User> getAllUsers();

    User findByUsername(String login);

}
