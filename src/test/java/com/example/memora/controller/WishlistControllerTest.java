package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.model.Wishlist;
import com.example.memora.service.SharedWishlistService;
import com.example.memora.service.UserService;
import com.example.memora.service.WishService;
import com.example.memora.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class WishlistControllerTest {
    @MockitoBean
    private WishlistService wishlistService;

    @MockitoBean
    private WishService wishService;

    @MockitoBean
    private SharedWishlistService sharedWishlistService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMyWishlists() throws Exception {

        User user = new User();
        user.setId(1);

        Wishlist wishlist = new Wishlist();
        wishlist.setId(10);

        when(wishlistService.getWishlists(1)).thenReturn(List.of(wishlist));
        when(sharedWishlistService.getWishlistsSharedWithUser(1)).thenReturn(List.of());

        mockMvc.perform(get("/wishlists")
                        .sessionAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/myWishlists"))
                .andExpect(model().attributeExists("myWishlists"))
                .andExpect(model().attributeExists("sharedWishlists"));

        verify(wishlistService).getWishlists(1);
    }

    @Test
    void newWishlist() throws Exception {

        mockMvc.perform(get("/wishlists/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/addNewWishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    void saveWishlist() throws Exception {

        User user = new User();
        user.setId(1);

        mockMvc.perform(post("/wishlists")
                        .sessionAttr("user", user)
                        .param("name", "Birthday Wishlist"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));

        ArgumentCaptor<Wishlist> captor = ArgumentCaptor.forClass(Wishlist.class);

        verify(wishlistService).saveWishlist(captor.capture(), eq(1));

        assertEquals("Birthday Wishlist", captor.getValue().getTitle());
    }

    @Test
    void showWishlist() throws Exception {

        User user = new User();
        user.setId(1);

        Wishlist wishlist = new Wishlist();
        wishlist.setId(10);

        when(wishlistService.getWishlist(10)).thenReturn(wishlist);
        when(wishlistService.canEdit(any(), any())).thenReturn(true);
        when(wishService.getWishes(10)).thenReturn(List.of());

        mockMvc.perform(get("/wishlists/10")
                        .sessionAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/wishlist"))
                .andExpect(model().attributeExists("wishlist"))
                .andExpect(model().attributeExists("wishes"))
                .andExpect(model().attributeExists("canEdit"));

        verify(wishService).getWishes(10);
    }

    @Test
    void editWishlist() throws Exception {

        Wishlist wishlist = new Wishlist();
        wishlist.setId(10);

        when(wishlistService.getWishlist(10)).thenReturn(wishlist);

        mockMvc.perform(get("/wishlists/10/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/editWishlist"))
                .andExpect(model().attributeExists("wishlist"));

        verify(wishlistService).getWishlist(10);
    }

    @Test
    void updateWishlist() throws Exception {

        mockMvc.perform(post("/wishlists/10")
                        .param("title", "Updated Wishlist"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));

        ArgumentCaptor<Wishlist> captor = ArgumentCaptor.forClass(Wishlist.class);

        verify(wishlistService).updateWishlist(eq(10), captor.capture());

        assertEquals("Updated Wishlist", captor.getValue().getTitle());
    }

    @Test
    void deleteWishlist() throws Exception {

        mockMvc.perform(post("/wishlists/10/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));

        verify(wishlistService).deleteWishlist(10);
    }
}