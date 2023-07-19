package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Set;


@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<Role> getAllRoles() {
        return (Set<Role>) roleRepository.findAll();
    }

    @Override
    public void saveOrUpdateRole(Role role) {
        roleRepository.save(role);
    }
}
