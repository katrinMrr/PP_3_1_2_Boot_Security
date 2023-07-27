package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public ModelAndView newUserForm() {
        ModelAndView mav = new ModelAndView("new");
        mav.addObject("user", new User());
        return mav;
    }

    @PutMapping("/create")
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
        User user = userService.findUserByID(id);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRolesSet().contains(roleService.findByNameRole("ROLE_ADMIN")));
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "admin", required = false) String role) {
        if (role != null) {
            user.setRolesSet(roleService.getAllRoles());
        } else {
            user.setRolesSet(roleService.getAllRoles().stream().filter(r -> r.getNameRole()
                    .equals("ROLE_USER")).collect(Collectors.toSet()));
        }
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}

