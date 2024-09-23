package com.brytvich.helpdesk.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "person")
public class Person {

    private int id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String role;

    private int birthDate;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Ticket> tickets;

}
