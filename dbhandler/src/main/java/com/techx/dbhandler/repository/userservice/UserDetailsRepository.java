package com.techx.dbhandler.repository.userservice;

import com.techx.dbhandler.models.userservice.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByEmailId(String emailId);
    UserDetails findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM userdetails u WHERE u.emailid = ?1 AND u.password = ?2", nativeQuery = true)
    UserDetails findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE userdetails u SET u.active = :active WHERE u.userid = :userid", nativeQuery = true)
    int updateUserDetailsSetActiveForUserid(@Param("userid") String userid, @Param("active") String active);

    @Transactional
    @Modifying
    @Query(value = "UPDATE userdetails u SET u.status = :status WHERE u.userid = :userid", nativeQuery = true)
    int updateUserDetailsSetStatusForUserid(@Param("userid") String userid, @Param("status") String status);
}
