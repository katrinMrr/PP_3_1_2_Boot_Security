package ru.kata.spring.boot_security.demo.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class PostUnit {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void postConstruct() {
        roleService.saveOrUpdateRole(new Role("ROLE_ADMIN"));
        roleService.saveOrUpdateRole(new Role("ROLE_USER"));

        String password = "321";
        User adminUser = new User("Katya", "Woman", "admin", password, roleService.getAllRoles());
        userService.saveOrUpdateUser(adminUser);
        System.out.println("username = " + adminUser.getUsername() + " password = " + password);

    }
}
