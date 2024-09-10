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
@Table(name = "internship_program")
public class InternshipProgram extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    private Business business;

    @OneToMany(mappedBy = "internshipProgram")
    private List<Application> applications = new ArrayList<>();

    private String titleName;
    private String description;
    private String picture;
    private String benefits;
    private String requirements;
    private String skillsAndKeywordRelate;
    private String location;
    private Integer duration;
    private Date applicationDeadline;
    private String status;
}
