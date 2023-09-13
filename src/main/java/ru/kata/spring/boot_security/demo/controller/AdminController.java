package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public static final String REST_URL = "/admin/users";

    @Autowired
    public AdminController(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping()
    public Set<User> printUsers() {
        Set<User> userSet = userService.findAllUsers();
        userSet.forEach(user -> user.setPassword(null));
        return userSet;
    }

    @GetMapping("/current")
    public ResponseEntity<User> currentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        user.getRolesSet().forEach(r -> r.setNameRole(r.getNameRole().substring(5)));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        newUser.setRolesSet(new HashSet<>());
        userService.saveOrUpdateUser(newUser);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newUser);
    }


    @PatchMapping(value = "edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user,
                           @RequestParam(value = "editRole", required = false) String role,
                           @RequestParam(value = "editGender", required = false) String gender) {
        User userFromBase = userService.findUserByID(user.getId());
        user.setGender(gender == null ? "" : gender);
        user.setPassword(user.getPassword() == null || user.getPassword().equals("")
                ? userFromBase.getPassword()
                : bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRolesSet(role != null && role.equals("ADMIN")
                ? roleService.getAllRoles()
                : roleService.getAllRoles().stream().filter(r -> r.getNameRole()
                .equals("ROLE_USER")).collect(Collectors.toSet()));
        userService.saveOrUpdateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}

