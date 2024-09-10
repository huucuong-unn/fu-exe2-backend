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
@Table(name = "business")
public class Business extends BaseEntity {

    @OneToMany(mappedBy = "business")
    private List<InternshipProgram> internshipPrograms = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String name;

    private String industry;

    private String location;

    private String description;

    private String logoPicture;

    private String backgroundPicture;

    private String status;
}
