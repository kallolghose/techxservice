package com.techx.dbhandler.repository.userservice;

import com.techx.dbhandler.models.userservice.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByEmailId(String emailId);
    UserDetails findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM userdetails u WHERE u.emailid = ?1 and u.password = ?2", nativeQuery = true)
    UserDetails findByEmailAndPassword(String email, String password);
}
