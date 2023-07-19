package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;


public interface UserService  {
    void saveOrUpdateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

   Set<User> getAllUsers();

}
