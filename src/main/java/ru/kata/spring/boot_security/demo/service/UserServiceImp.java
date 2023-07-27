package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public boolean saveOrUpdateUser(User user) {
        if (user.getId() == null && userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.getRolesSet().add(roleService.getAllRoles().stream().filter(r -> r.getNameRole()
                .equals("ROLE_USER")).findFirst().orElse(null));
        if (user.getId() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User findUserByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Set<User> findAllUsers() {
        Set<User> set = new HashSet<>();
        Iterable<User> iterable = userRepository.findAll();
        iterable.forEach(set::add);
        return set;
    }

    @Override
    public User findByUsername(String login) {
        return userRepository.findByUsername(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}

