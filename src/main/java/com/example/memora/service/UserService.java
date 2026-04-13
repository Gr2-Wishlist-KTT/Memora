package com.example.memora.service;

import com.example.memora.model.User;
import com.example.memora.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService (UserRepository repository){
        this.repository = repository;
    }

    public User findUserById(String email){
        return repository.findUserById(email);
    }

}
