package com.cardosotv.quizai.model.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cardosotv.quizai.model.entities.Option;

public interface OptionRepository extends JpaRepository<Option, UUID> {

}
