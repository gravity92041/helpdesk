package com.brytvich.helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "ticket")
public class Ticket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_closed")
    private LocalDateTime dateClosed;

    @Column(name = "date_opened")
    private LocalDateTime dateOpened;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;
    @Column(name = "responsible")
    private int responsible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author",referencedColumnName = "id")
    private Person author;
    @Column(name = "status")
    private String status;
}
