package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public ModelAndView newUserForm() {
        ModelAndView mav = new ModelAndView("new");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "admin", required = false) String role) {
        user.setRolesSet(new HashSet<>());
        if (role != null) {
            user.getRolesSet().add(roleService.findByNameRole("ROLE_ADMIN"));
        }
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String showUserById(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRolesSet().contains(roleService.findByNameRole("ROLE_ADMIN")));
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "admin", required = false) String role) {
        if (role != null) {
            User user1 = userService.getUserById(user.getId());
            user.setRolesSet(user1.getRolesSet());
            user.setUsername(user1.getUsername());
            user.setPassword(user1.getPassword());
            user.getRolesSet().add(roleService.findByNameRole("ROLE_ADMIN"));
        } else {
            User user1 = userService.getUserById(user.getId());
            user.setRolesSet(user1.getRolesSet());
            user.setUsername(user1.getUsername());
            user.setPassword(user1.getPassword());
            user.getRolesSet().remove(roleService.findByNameRole("ROLE_ADMIN"));
        }
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}

