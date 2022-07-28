package com.epam.libraryservice.clients;

import com.epam.libraryservice.dto.BookDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="book-service/books",fallback = BookClientImpl.class)
@LoadBalancerClient(value = "book-service",configuration = BookClientImpl.class)
public interface BookClient {

    @GetMapping
    ResponseEntity<List<BookDto>> getAllBooks();

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> getABook(@PathVariable int bookId);

    @PostMapping
    ResponseEntity<BookDto> addABook(@RequestBody BookDto bookDto);

    @PutMapping("/{bookId}")
    ResponseEntity<BookDto> updateABook(@PathVariable int bookId, @RequestBody @Valid BookDto bookDto);

    @DeleteMapping("/{bookId}")
    ResponseEntity<BookDto> deleteABook(@PathVariable int bookId);
}
