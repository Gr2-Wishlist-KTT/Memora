package com.example.memora.controller;

import com.example.memora.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NavController.class)
class NavControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void frontPageUserNotLoggedIn() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("nav/frontpage"));
    }

    @Test
    void frontPageUserLoggedIn() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new User());

        mockMvc.perform(get("/").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));
    }

    @Test
    void about() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("nav/about"));
    }

    @Test
    void contact() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("nav/contact"));
    }
}