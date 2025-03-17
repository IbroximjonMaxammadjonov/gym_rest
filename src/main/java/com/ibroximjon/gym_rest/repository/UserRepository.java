package com.ibroximjon.gym_rest.repository;

import com.ibroximjon.gym_rest.model.User;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {



}
