package com.techx.dbhandler.repository.userservice;

import com.techx.dbhandler.models.userservice.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findByRole(String role);
}
