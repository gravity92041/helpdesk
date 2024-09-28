package com.brytvich.helpdesk.repositories;

import com.brytvich.helpdesk.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<Person,Integer> {
}
