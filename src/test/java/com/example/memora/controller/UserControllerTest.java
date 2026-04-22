package com.example.memora.controller;

import com.example.memora.model.User;

import com.example.memora.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private MockHttpSession session;
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("1234");

        session = new MockHttpSession();
        session.setAttribute("user", user);
    }

    @Test
    void registerPage() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("email", "new@example.com")
                        .param("password", "pw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(captor.capture());

        User newUser = captor.getValue();
        assertEquals("new@example.com", newUser.getEmail());
        assertEquals("pw", newUser.getPassword());
    }

    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void loginSuccess() throws Exception {
        when(userService.login("test@example.com", "1234")).thenReturn(true);
        when(userService.findUserByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(post("/auth/login")
                        .param("email", "test@example.com")
                        .param("password", "1234")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));
    }

    @Test
    void loginFail() throws Exception {
        when(userService.login("wrong@example.com", "bad")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .param("email", "wrong@example.com")
                        .param("password", "bad"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attributeExists("wrongCredentials"));
    }

    @Test
    void showEditProfileNotLoggedIn() throws Exception {
        mockMvc.perform(get("/auth/editProfile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void showEditProfileLoggedIn() throws Exception {
        mockMvc.perform(get("/auth/editProfile").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/editProfile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void editProfilePostUpdatesUserAndRedirects() throws Exception {
        when(userService.findUserByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(post("/auth/editProfile")
                        .session(session)
                        .param("email", "test@example.com")
                        .param("password", "newpw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService).editProfile(captor.capture());

        User newUser = captor.getValue();
        assertEquals("test@example.com", newUser.getEmail());
        assertEquals("newpw", newUser.getPassword());
    }

    @Test
    void showForgotPassword() throws Exception {
        mockMvc.perform(get("/auth/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/forgot-password"));
    }

    @Test
    void forgotPasswordEmailNotFound() throws Exception {
        when(userService.findUserByEmail("wrong@example.com")).thenReturn(null);

        mockMvc.perform(post("/auth/forgot-password")
                        .param("email", "wrong@example.com")
                        .param("newPassword", "newpw"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/forgot-password"))
                .andExpect(model().attributeExists("EmailNotFound"));
    }

    @Test
    void forgotPasswordSuccess() throws Exception {
        when(userService.findUserByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(post("/auth/forgot-password")
                        .param("email", "test@example.com")
                        .param("newPassword", "newpw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));

        verify(userService).editProfile(user);
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/auth/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assert session.isInvalid();
    }
}