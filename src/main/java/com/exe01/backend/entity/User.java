package com.exe01.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;
    private String name;
    private String email;
    private Date dob;
    private String password;
    private String role;
    private String status;
    private Date startDateSubscription;
    private Date expiryDateSubscription;
    private int remainReviewCVTimes=0;
    private int remainInterviewTimes=0;
}
