package com.jhkwak.preorder.repository.user;

import com.jhkwak.preorder.entity.user.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByUserIdAndStatus(Long Id, Character status);

    Optional<EmailVerification> findByTokenAndStatus(String token, Character status);
}
