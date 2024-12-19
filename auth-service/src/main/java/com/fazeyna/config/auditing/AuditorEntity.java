package com.fazeyna.config.auditing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditorEntity {

    @CreatedDate
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(length = 50)
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedOn;

    @LastModifiedBy
    @Column(length = 50)
    private String updatedBy;

    private LocalDateTime deletedOn;

    @Column(length = 50)
    private String deletedBy;

    @Column(length = 50)
    private Boolean isDeleted = false;

    @PreUpdate
    @PrePersist
    public void beforeAnyUpdate() {
        if (isDeleted != null && isDeleted) {

//            if (deletedBy == null) {
//               deletedBy = AuthenticationHelper.getUserId().toString();
//            }

            if (getDeletedOn() == null) {
                deletedOn = LocalDateTime.now();
            }
        }
    }

}
