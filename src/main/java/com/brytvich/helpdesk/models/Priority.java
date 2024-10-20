package com.brytvich.helpdesk.models;

import lombok.Data;

import jakarta.persistence.*;
@Data
@Entity
@Table(name = "priorities")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String level;


}
