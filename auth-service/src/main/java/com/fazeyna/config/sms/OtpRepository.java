package com.fazeyna.config.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Integer> {

    Optional<OTP> findByCode(String code);
}
