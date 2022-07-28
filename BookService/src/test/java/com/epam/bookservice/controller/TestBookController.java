package com.epam.bookservice.controller;

import com.epam.bookservice.dto.BookDTO;
import com.epam.bookservice.entities.Book;
import com.epam.bookservice.exceptions.ResourceNotFoundException;
import com.epam.bookservice.service.BookServiceImpl;
import com.epam.bookservice.util.TestUtils;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
    class TestBookController {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookServiceImpl bookService;
    BookDTO bookDto;
    Book book;

    @BeforeEach
    void setUp() {
        this.book = TestUtils.createBook();
        this.bookDto = TestUtils.createBookDto();
    }

    @Test
    void testAddBook() throws Exception {
        when(bookService.add(bookDto)).thenReturn(bookDto);
        Gson gson = new Gson();
        String bookJson = gson.toJson(bookDto);
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(status().isCreated());

    }

    @Test
    void testGetBook() throws Exception {
        when(bookService.get(Mockito.anyInt())).thenReturn(bookDto);

        mockMvc.perform(get("/books/12")).andExpect(status().isOk());

    }

    @Test
    void testGetNonExistingBook() throws Exception {
        Mockito.when(bookService.get(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/books/12")).andExpect(status().isNotFound());

    }

    @Test
    void testDeleteBook() throws Exception {
        Mockito.doNothing().when(bookService).remove(Mockito.anyInt());
        mockMvc.perform(delete("/books/12")).andExpect(status().isNoContent());

    }

    @Test
    void testGetAll() throws Exception {
        List<BookDTO> bookDtos = new ArrayList<>();
        Mockito.when(bookService.getAll()).thenReturn(bookDtos);
        mockMvc.perform(get("/books")).andExpect(status().isOk());

    }

    @Test
    void updateBook() throws Exception {
        Mockito.doNothing().when(bookService).update(Mockito.any());
        Mockito.when(bookService.get(Mockito.anyInt())).thenReturn(bookDto);
        Gson gson = new Gson();
        String bookJson = gson.toJson(bookDto);
        mockMvc.perform(put("/books/12").contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(status().isOk());
        bookDto.setAuthor(null);
        bookJson = gson.toJson(bookDto);
        mockMvc.perform(put("/books/12").contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(status().isBadRequest());

    }



}
