package com.epam.libraryservice.controller;

import com.epam.libraryservice.clients.BookClient;
import com.epam.libraryservice.clients.UserClient;
import com.epam.libraryservice.dto.BookDto;
import com.epam.libraryservice.dto.ExceptionResponse;
import com.epam.libraryservice.dto.LibraryDto;
import com.epam.libraryservice.dto.UserDto;
import com.epam.libraryservice.exception.BookException;
import com.epam.libraryservice.service.LibraryServiceImpl;
import com.epam.libraryservice.util.TestUtil;
import com.google.gson.Gson;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
  class TestLibraryController {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private LibraryServiceImpl libraryService;
    @MockBean
    private BookClient bookClient;
    @MockBean
    private UserClient userClient;
    TestUtil testUtil = new TestUtil();

    @Test
    void getAllBooksTest() throws Exception {
        List<BookDto> books = testUtil.getBooks();
        when(libraryService.getAllBooks()).thenReturn(books);
        mockMvc.perform(get("/library/books")).andExpect(status().isOk());
    }

    @Test
    void getBookTest() throws Exception {
        BookDto bookDto = testUtil.getBook();
        when(libraryService.getABook(Mockito.anyInt())).thenReturn(bookDto);
        mockMvc.perform(get("/library/books/1")).andExpect(status().isOk());
    }


    @Test
    void addBookTest() throws Exception {
        BookDto bookDto = testUtil.getBook();
        when(libraryService.addABook(bookDto)).thenReturn(bookDto);
        String bookJson = new Gson().toJson(bookDto);
        mockMvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(status().isOk());

    }

    @Test
    void addBookTest1() throws Exception {
        BookDto bookDto = testUtil.getBook();
        bookDto.setName("");
        when(libraryService.addABook(bookDto)).thenReturn(bookDto);
        String bookJson = new Gson().toJson(bookDto);
        mockMvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void updateBookTest() throws Exception {
        BookDto bookDto = testUtil.getBook();
        when(libraryService.updateABook(1, bookDto)).thenReturn(bookDto);
        String bookJson = new Gson().toJson(bookDto);
        mockMvc.perform(put("/library/books/1").contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBookTest() throws Exception {
        doNothing().when(libraryService).deleteABook(Mockito.anyInt());
        mockMvc.perform(delete("/library/books/1")).andExpect(status().isNoContent());
    }

    @Test
    void getUsersTest() throws Exception {
        when(libraryService.getAllUsers()).thenReturn(testUtil.getUsers());
        mockMvc.perform(get("/library/users")).andExpect(status().isOk());
    }

    @Test
    void getUserTest() throws Exception {
        when(libraryService.getAUser("shubham")).thenReturn(testUtil.getUser());
        mockMvc.perform(get("/library/users/shubham")).andExpect(status().isOk());
    }


    @Test
    void addUserTest() throws Exception {
        when(libraryService.addAUser(testUtil.getUsers().get(0))).thenReturn(testUtil.getUsers().get(0));
        String userJson = new Gson().toJson(testUtil.getUsers().get(0));
        mockMvc.perform(post("/library/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserTest() throws Exception {
        when(libraryService.updateAUser("shubham", testUtil.getUsers().get(0))).thenReturn(testUtil.getUsers().get(0));
        String userJson = new Gson().toJson(testUtil.getUsers().get(0));
        mockMvc.perform(put("/library/users/shubham").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void issueABookTest() throws Exception {
        LibraryDto libraryDto = new LibraryDto(1, "shubham", 1);
        when(libraryService.issueBook("shubham", 1)).thenReturn(libraryDto);
        mockMvc.perform(post("/library/users/shubham/books/1")).andExpect(status().isOk());

    }

    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(libraryService).deleteAUser(Mockito.anyString());
        mockMvc.perform(delete("/library/users/shubham")).andExpect(status().isOk());
    }

    @Test
    void releaseBookTest() throws Exception{

        when(libraryService.release(Mockito.anyString(),Mockito.anyInt())).thenReturn("book");
        mockMvc.perform(delete("/library/users/Shubham/books/1")).andExpect(status().isOk());

    }


    @Test
    void releaseBookTest2() throws Exception {

        when(libraryService.release(Mockito.anyString(), Mockito.anyInt())).thenThrow(BookException.class);
        mockMvc.perform(delete("/library/users/gaurish/books/1")).andExpect(status().isOk());

    }


    @Test
    void testIssueBookInvalidUserName() throws Exception {
        Gson gson = new Gson();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus("NOT_FOUND");
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        Mockito.doThrow(
                        new FeignException.NotFound("", request, gson.toJson(exceptionResponse).getBytes(), new HashMap<>()))
                .when(libraryService).issueBook("shubham", 2);
        mockMvc.perform(post("/library/users/shubham/books/2")).andExpect(status().isNotFound());



    }



    @Test
    void testBookNonExisting() throws Exception {
        Gson gson = new Gson();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus("NOT_FOUND");
        HttpClientErrorException http = HttpClientErrorException.NotFound.create(HttpStatus.NOT_FOUND, "", null,
                gson.toJson(exceptionResponse).getBytes(), null);
        Mockito.doThrow(http).when(libraryService).getABook(1);

        mockMvc.perform(get("/library/books/1")).andExpect(status().is4xxClientError());



    }







}
