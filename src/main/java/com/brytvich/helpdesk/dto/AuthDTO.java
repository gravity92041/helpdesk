package com.brytvich.helpdesk.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть")
    private String username;

    @NotEmpty
    private String password;

    private int birthYear;
}
