package com.brytvich.helpdesk.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonDTO {
    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2,max = 30,message = "Имя должно быть")
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "birthyear")
    private int birthYear;
}
