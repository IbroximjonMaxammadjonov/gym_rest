package com.ibroximjon.gym_rest.service;

import com.ibroximjon.gym_rest.repository.UserRepository;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
                this.userRepository = userRepository;
            }


}
