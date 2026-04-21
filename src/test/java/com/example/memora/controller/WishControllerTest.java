package com.example.memora.controller;

import com.example.memora.model.Wish;
import com.example.memora.service.WishService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static jdk.dynalink.beans.StaticClass.forClass;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

@WebMvcTest(WishController.class)
class WishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishService wishService;


    @Test
    void saveWishes() throws Exception {

        mockMvc.perform(post("/wishlists/{wishlistId}/wishes", 1)
                        .param("productName", "Nike Tshirt")
                        .param("description", "En trænings T-shirt, Grå, Str. ")
                        .param("linkToProduct", "www.nikestore.com")
                        .param("quantity", "1")
                        .param("price", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists/1"));

        ArgumentCaptor<Wish> captor = ArgumentCaptor.forClass(Wish.class);
        verify(wishService).saveWishes(captor.capture(), eq(1));

        Wish wish = captor.getValue();

        assertEquals("Nike Tshirt", wish.getProductName());
        assertEquals("En trænings T-shirt, Grå, Str. ", wish.getDescription());
        assertEquals("www.nikestore.com", wish.getLinkToProduct());
        assertEquals(10.0, wish.getPrice());
        assertEquals(1, wish.getQuantity());
    }

    @Test
    void editWish() throws Exception {

        mockMvc.perform(post("/wishlists/1/wishes/5")
                        .param("productName", "opdateret Nike Tshirt")
                        .param("description", "opdateret beskrivelse")
                        .param("linkToProduct", "www.opdateret.com")
                        .param("quantity", "2")
                        .param("price", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists/1"));

        ArgumentCaptor<Wish> captor = ArgumentCaptor.forClass(Wish.class);

        verify(wishService).updateWish(captor.capture());

        Wish wish = captor.getValue();

        assertEquals("opdateret Nike Tshirt", wish.getProductName());
        assertEquals("opdateret beskrivelse", wish.getDescription());
        assertEquals("www.opdateret.com", wish.getLinkToProduct());
        assertEquals(2, wish.getQuantity());
        assertEquals(15.0, wish.getPrice());
        assertEquals(5, wish.getId());
    }
    @Test
    void deleteWish() throws Exception {

        mockMvc.perform(post("/wishlists/1/wishes/5/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists/1"));

        verify(wishService).removeWish(5);
    }
}