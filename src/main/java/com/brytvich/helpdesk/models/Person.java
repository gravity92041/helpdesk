package com.brytvich.helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "person")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "firstname")
    @NotEmpty
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty
    private String lastname;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "birthyear")
    private int birthYear;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
