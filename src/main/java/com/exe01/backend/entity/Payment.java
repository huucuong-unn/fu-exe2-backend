package com.exe01.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "uniStudent_id", referencedColumnName = "id")
    private UniStudent uniStudent;

    private Double total;
    private String method;
    private String status;
}
