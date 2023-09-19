package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;


@RestController
@RequestMapping("/admin/users")
public class AdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping()
    public Set<User> printUsers() {
        Set<User> userSet = userService.findAllUsers();
        userSet.forEach(user -> user.setPassword(null));
        return userSet;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/new")
    public void createNewUser(@RequestBody User newUser) {
        userService.saveOrUpdateUser(newUser);
    }


    @PatchMapping(value = "edit")
    public void updateUser(@RequestBody User user) {
        User userFromBase = userService.findUserByID(user.getId());
        user.setPassword(user.getPassword() == null || user.getPassword().equals("")
                ? userFromBase.getPassword()
                : bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveOrUpdateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}

