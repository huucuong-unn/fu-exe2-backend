package com.exe01.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application")
public class Application extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "internship_id", referencedColumnName = "id")
    private InternshipProgram internshipProgram;

    @ManyToOne
    @JoinColumn(name = "unistudent_id", referencedColumnName = "id")
    private UniStudent uniStudent;

    private String cv;
    private String status;  // "submitted", "reviewed", "interview", "rejected", "accepted"
}
