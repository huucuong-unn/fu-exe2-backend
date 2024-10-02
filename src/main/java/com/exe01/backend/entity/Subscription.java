package com.exe01.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription extends BaseEntity{
    @OneToMany(mappedBy = "subscription")
    private List<User> user = new ArrayList<>();

    private Double price;
    private int reviewCVTime;
    private int interviewTime;
    private String planType;
    private Date startDate;
    private Date endDate;
}
