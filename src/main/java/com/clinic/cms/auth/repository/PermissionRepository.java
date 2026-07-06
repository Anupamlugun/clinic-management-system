package com.clinic.cms.auth.repository;

import com.clinic.cms.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByCode(String code);

    Optional<Permission> findByName(String name);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    Set<Permission> findByIdIn(Collection<Long> ids);
}