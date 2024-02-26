package com.cardosotv.quizai.model.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardosotv.quizai.model.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.deletedDate is null")
    public List<User> findAllUsersActive();

}