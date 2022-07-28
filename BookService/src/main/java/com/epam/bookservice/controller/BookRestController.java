package com.epam.bookservice.controller;

import com.epam.bookservice.dto.BookDTO;
import com.epam.bookservice.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/books")
@RestController
public class BookRestController {
    @Autowired
    BookServiceImpl bookService;


    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll());

    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> get(@PathVariable("bookId") int bookId) {

        return ResponseEntity.status(HttpStatus.OK).body(bookService.get(bookId));
    }

    @PostMapping
    public ResponseEntity<BookDTO> add(@RequestBody @Valid BookDTO bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.add(bookDto));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<BookDTO> delete(@PathVariable("bookId") int bookId) {
        bookService.remove(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> update(@RequestBody @Valid BookDTO updatedBook,
                                          @PathVariable("bookId") int bookId) {

        bookService.get(bookId);
        updatedBook.setId(bookId);
        bookService.update(updatedBook);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);


    }





}
