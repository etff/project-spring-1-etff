package com.mogaco.project.member.infra;

import com.mogaco.project.member.domain.Role;
import com.mogaco.project.member.domain.RoleRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaRoleRepository extends RoleRepository, CrudRepository<Role, Long> {
    List<Role> findAllByUserId(Long userId);

    Role save(Role role);
}
