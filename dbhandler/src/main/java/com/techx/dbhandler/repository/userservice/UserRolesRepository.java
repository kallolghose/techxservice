package com.techx.dbhandler.repository.userservice;

import com.techx.dbhandler.models.userservice.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository  extends JpaRepository<UserRoles, Long> {

    UserRoles findByUserId(String userId);

}
