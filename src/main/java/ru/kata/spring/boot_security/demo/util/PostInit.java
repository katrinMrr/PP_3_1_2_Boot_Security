package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class PostInit {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void postConstruct() {
        roleService.saveOrUpdateRole(new Role("ROLE_ADMIN"));
        roleService.saveOrUpdateRole(new Role("ROLE_USER"));

        String password = "321";
        User adminUser = new User("Katya", "Woman", "admin", password);
        adminUser.setIsAdmin(true);
        userService.saveOrUpdateUser(adminUser);
        System.out.println("username = " + adminUser.getUsername() + " password = " + password);


        User nUser1 = new User("user2", "Woman", "user2", password);
        nUser1.setIsAdmin(true);
        userService.saveOrUpdateUser(nUser1);


        User nUser2 = new User("user1", "Man", "user1", password);
        userService.saveOrUpdateUser(nUser2);
    }
}
