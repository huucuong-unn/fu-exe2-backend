package com.exe01.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "unistudent")
public class UniStudent extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "uniStudent")
    private List<Application> applications = new ArrayList<>();

    private Integer remainSubscription;
    private String fullName;
    private String university;
    private String major;
    private String cv;
    private String profilePicture;
}
