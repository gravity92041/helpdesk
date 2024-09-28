package com.brytvich.helpdesk.repositories;

import com.brytvich.helpdesk.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
