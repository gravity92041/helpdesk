package com.brytvich.helpdesk.models;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ticket {

    private int id;

    private String dateClosed;

    private String dateOpened;

    private String description;

    private String category;

    private String title;

    private int responsible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author",referencedColumnName = "id")
    private Person author;

    private String varchar;
}
