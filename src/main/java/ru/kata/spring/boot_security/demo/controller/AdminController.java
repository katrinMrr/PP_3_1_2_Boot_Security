package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping()
    public String printUsers(ModelMap model, Principal principal) {
        var userSet = userService.findAllUsers();
        userSet.forEach(user -> user.setPassword(null));
        model.addAttribute("users", userSet);
        User user = userService.findByUsername(principal.getName());
        user.getRolesSet().forEach(r -> r.setNameRole(r.getNameRole().substring(5)));
        model.addAttribute("user", user);
        model.addAttribute("userForm", new User());
        return "admin";
    }

    @GetMapping("/new")
    public ModelAndView newUserForm() {
        ModelAndView mav = new ModelAndView("new");
        mav.addObject("user", new User());
        return mav;
    }

    @PutMapping("/create")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "createRole") String role,
                             @RequestParam(value = "createGender", required = false) String gender) {
        user.setRolesSet(role.equals("ADMIN") ? roleService.getAllRoles()
                : roleService.getAllRoles().stream().filter(r -> r.getNameRole()
                .equals("ROLE_USER")).collect(Collectors.toSet()));
        user.setGender(gender);
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
    public String updateUser(@ModelAttribute("user") User user,
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
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

