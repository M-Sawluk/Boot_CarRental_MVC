package com.michal.springboot.repository;

import com.michal.springboot.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mike on 2017-03-16.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleName(String roleName);
}
