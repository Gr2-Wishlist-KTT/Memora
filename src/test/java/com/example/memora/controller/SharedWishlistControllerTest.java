package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.model.Wishlist;
import com.example.memora.service.SharedWishlistService;
import com.example.memora.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SharedWishlistController.class)
class SharedWishlistControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    private SharedWishlistService sharedWishlistService;
    @MockitoBean
    private WishlistService wishlistService;

    private MockHttpSession session;
    private User user;
    private Wishlist wishlist;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1);

        wishlist = new Wishlist();
        wishlist.setId(10);
        wishlist.setOwner(1);

        session = new MockHttpSession();
        session.setAttribute("user", user);
    }

    @Test
    void addShareWishlistUserNotLoggedIn() throws Exception {
        mockMvc.perform(get("/wishlists/1/share"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void addShareWishlistUserLoggedIn() throws Exception {
        when(wishlistService.getWishlist(10)).thenReturn(wishlist);
        when(sharedWishlistService.findViewersForWishlist(10))
                .thenReturn(List.of());

        mockMvc.perform(get("/wishlists/10/share").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/addShareWishlist"))
                .andExpect(model().attributeExists("wishlist"))
                .andExpect(model().attributeExists("viewers"));
    }

    @Test
    void saveShareWishlistSuccess() throws Exception {
        mockMvc.perform(post("/wishlists/10/share")
                        .param("email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists/10/share"));
    }

    @Test
    void saveShareWishlistWrongEmail() throws Exception {
        when(wishlistService.getWishlist(10)).thenReturn(wishlist);
        when(sharedWishlistService.findViewersForWishlist(10))
                .thenReturn(List.of());

        doThrow(new IllegalArgumentException("Fejl!"))
                .when(sharedWishlistService).shareWishlist(10, "bad@example.com");

        mockMvc.perform(post("/wishlists/10/share")
                        .param("email", "bad@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/addShareWishlist"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("wishlist"))
                .andExpect(model().attributeExists("viewers"));
    }

    @Test
    void deleteShareFromFrontPage() throws Exception {
        mockMvc.perform(post("/wishlists/10/share/delete")
                        .param("viewerId", "5")
                        .param("source", "front"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));
    }

    @Test
    void deleteShareFromSharePage() throws Exception {
        mockMvc.perform(post("/wishlists/10/share/delete")
                        .param("viewerId", "5")
                        .param("source", "share"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists/10/share"));
    }
}