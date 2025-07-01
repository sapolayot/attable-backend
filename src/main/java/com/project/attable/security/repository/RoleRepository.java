package com.project.attable.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Role;
import com.project.attable.entity.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleNameIn(RoleName roleName);
}
