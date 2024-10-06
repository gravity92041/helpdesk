package com.brytvich.helpdesk.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketDTO {
    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;
}
