package com.cardosotv.quizai.model.repositories;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardosotv.quizai.model.entities.Subject;


public interface SubjectRepository extends JpaRepository<Subject, UUID>{

    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    public List<Subject> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM Subject s WHERE s.isActive = true")
    public List<Subject> findActivatedSubjects();

}


