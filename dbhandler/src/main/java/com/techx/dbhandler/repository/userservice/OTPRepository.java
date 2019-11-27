package com.techx.dbhandler.repository.userservice;

import com.techx.dbhandler.models.userservice.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<Otp, Long> {

    Otp findByPhoneNumber(String phoneNumber);
}
