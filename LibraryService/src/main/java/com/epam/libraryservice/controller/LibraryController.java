package com.epam.libraryservice.controller;

import com.epam.libraryservice.dto.*;
import com.epam.libraryservice.exception.BookException;
import com.epam.libraryservice.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    LibraryService libraryService;
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        return new ResponseEntity<>(libraryService.getAllBooks(), HttpStatus.OK);
    }
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookDto> getABook(@PathVariable int bookId){
        return new ResponseEntity<>(libraryService.getABook(bookId),HttpStatus.OK);
    }
    @PostMapping("/books")
    public ResponseEntity<BookDto> addABook(@RequestBody @Valid BookDto bookDto){
        return new ResponseEntity<>(libraryService.addABook(bookDto),HttpStatus.OK);
    }
    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookDto> updateABook(@PathVariable int bookId, @RequestBody @Valid BookDto bookDto){
        return new ResponseEntity<>(libraryService.updateABook(bookId,bookDto),HttpStatus.OK);
    }
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<BookDto> deleteABook(@PathVariable int bookId){
        libraryService.deleteABook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(libraryService.getAllUsers(),HttpStatus.OK);
    }
    @GetMapping("/users/{userName}")
    public ResponseEntity<UserWithBookDto> getAUser(@PathVariable String userName){
        return new ResponseEntity<>(libraryService.getAUser(userName),HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<UserDto> addAUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(libraryService.addAUser(userDto),HttpStatus.OK);
    }
    @PutMapping("/users/{userName}")
    public ResponseEntity<UserDto> updateAUser(@PathVariable String userName, @Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(libraryService.updateAUser(userName,userDto),HttpStatus.OK);
    }
    @PostMapping("/users/{userName}/books/{bookId}")
    public ResponseEntity<LibraryDto> issueBook(@PathVariable String userName, @PathVariable int bookId) throws BookException {
        return new ResponseEntity<>(libraryService.issueBook(userName,bookId),HttpStatus.OK);
    }
    @DeleteMapping("/users/{userName}")
    public ResponseEntity<UserDto> deleteAUser(@PathVariable String userName){
        libraryService.deleteAUser(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/users/{userName}/books/{bookId}")
    public ResponseEntity<String> releaseABook(@PathVariable String userName,@PathVariable int bookId) throws BookException {
        return new ResponseEntity<>(libraryService.release(userName, bookId), HttpStatus.OK);
    }



}
