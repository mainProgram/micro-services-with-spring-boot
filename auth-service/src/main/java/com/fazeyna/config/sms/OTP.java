package com.fazeyna.config.sms;

import com.fazeyna.token.TokenType;
import com.fazeyna.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

    @Id
    @GeneratedValue
    public Integer id;

    public String code;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;

    private LocalDateTime creationTime;

    @PrePersist
    public void create() {
        this.creationTime = LocalDateTime.now();
        this.expired = false;
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return expired || creationTime.plusMinutes(1).isBefore(now);
    }

}
