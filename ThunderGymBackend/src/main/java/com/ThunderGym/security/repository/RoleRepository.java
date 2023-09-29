package com.ThunderGym.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ThunderGym.security.entity.ERole;
import com.ThunderGym.security.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRoleName(ERole roleName);

}
