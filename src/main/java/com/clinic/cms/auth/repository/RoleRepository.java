package com.clinic.cms.auth.repository;

import com.clinic.cms.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(String code);

    Optional<Role> findByName(String name);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    Set<Role> findByIdIn(Collection<Long> ids);
}