package com.example.memora.service;

import com.example.memora.model.User;
import com.example.memora.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService (UserRepository repository){
        this.repository = repository;
    }

    public User findUserByEmail(String email){
        return repository.findUserByEmail(email);
    }

    public boolean login(String email, String password){
        User user = repository.findUserByEmail(email);
        if (user != null)
            // CHECKS IF INPUT EXISTS
            return user.getPassword().equals(password);
        // USER NOT FOUND
        return false;
    }


    // ( SKAL MÅSKE LAVES OM TIL PRIVATE)
    //TIDELIGERE OPGAVE LIGGER DEN I CONTROLLER
    public boolean isLoggedIn (HttpSession session) {
        return session.getAttribute("user") != null;
    }


    @Transactional
    public void saveUser(User user){
        repository.saveUser(user);
    }

    public void editProfile (User user){
        repository.editProfile(user);
    }


}
