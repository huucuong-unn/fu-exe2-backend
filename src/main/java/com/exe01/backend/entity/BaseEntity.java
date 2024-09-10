package com.exe01.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @Column(updatable = false)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @Column(name = "create_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @PrePersist
    protected void onCreate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            createdBy = "Anonymous";
            modifiedBy = "Anonymous";
        } else {
            createdBy = authentication.getName();
            modifiedBy = authentication.getName();
        }
        id = UUID.randomUUID();

        createdDate = new Date();
        modifiedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     modifiedBy = authentication.getName();
        modifiedDate = new Date();
    }
}
