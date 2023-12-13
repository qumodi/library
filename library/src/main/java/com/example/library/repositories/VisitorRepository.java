package com.example.library.repositories;

import com.example.library.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor,Long> {
    List<Visitor> findByName(String name);
    List<Visitor> findBySurname(String surname);
    List<Visitor> findByPhoneNumber(String phoneNumber);
}
