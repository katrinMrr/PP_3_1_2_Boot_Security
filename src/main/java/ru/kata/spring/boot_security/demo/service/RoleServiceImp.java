package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> getAllRoles() {
        Set<Role> set = new HashSet<>();
        Iterable<Role> iterable = roleRepository.findAll();
        iterable.forEach(set::add);
        return set;
    }
    @Override
    public void saveOrUpdateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findByNameRole(String name) {
        return roleRepository.findByNameRole(name);
    }
}
