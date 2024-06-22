package com.jhkwak.preorder.entity.user;

import com.jhkwak.preorder.entity.ExpiresTimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "email_verification")
@NoArgsConstructor
public class EmailVerification extends ExpiresTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;


    @Column(nullable = false)
    private Character status = 'S';

    public EmailVerification(User user, String token, LocalDateTime expiresAt) {
        this.user = user;
        this.token = token;
        this.setExpiresAt(expiresAt);
    }
}
