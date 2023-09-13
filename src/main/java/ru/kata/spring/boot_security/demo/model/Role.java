package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @Column(name = "nameRole")
    private String nameRole;
    @Transient
    @ManyToMany(mappedBy = "rolesSet")
    private Set<User> userRoles;

    public Role() {

    }

    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString() {
        return this.nameRole;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idRole == null) ? 0 : idRole.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (idRole == null) {
            if (other.idRole != null)
                return false;
        } else if (!idRole.equals(other.idRole))
            return false;
        return true;
    }

    @Override
    public String getAuthority() {
        return getNameRole();
    }
}
