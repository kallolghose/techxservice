package com.techx.dbhandler.repository.userservice;

import com.techx.dbhandler.models.userservice.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OTPRepository extends JpaRepository<Otp, Long> {

    List<Otp> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM otp u WHERE u.phonenumber = ?1 and u.expired = ?2 ORDER BY creationdate DESC", nativeQuery = true)
    List<Otp> findByPhoneNumberAndExpired(String phoneNumber, String expired);


    @Transactional
    @Modifying
    @Query(value = "UPDATE otp o SET o.expired = :expired WHERE o.id = :id")
    int updateOTPSetExpiredForId(@Param("expired") String expired, @Param(("id")) Long id);

}
