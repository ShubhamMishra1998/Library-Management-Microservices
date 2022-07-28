package com.epam.bookservice.service;

import com.epam.bookservice.dto.BookDTO;
import com.epam.bookservice.entities.Book;
import com.epam.bookservice.exceptions.ResourceNotFoundException;
import com.epam.bookservice.repository.BookRepository;
import com.epam.bookservice.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
 class TestBookService {
    @InjectMocks
    BookServiceImpl bookService;
    @Mock
    BookRepository bookRepository;
    @Mock
    ModelMapper modelMapper;
    BookDTO bookDTO;
    Book book;

    @BeforeEach
    void setUp() {
        this.bookDTO = TestUtils.createBookDto();
        this.book = TestUtils.createBook();
    }

    @Test
    void testAddBook() {
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);
        Mockito.when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        Mockito.when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
        BookDTO actual = bookService.add(bookDTO);
        Assertions.assertEquals(bookDTO.getName(), actual.getName());

    }

    @Test
    void testGetBook() {
        BookDTO bookDto = new BookDTO();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setName(book.getName());
        bookDto.setPublisher(book.getPublisher());
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
        Mockito.when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDto);
        BookDTO actual = bookService.get(12);
        Assertions.assertEquals(bookDto.getName(), actual.getName());

    }

    @Test
    void testGetNonExistingBook() {
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.get(1));

    }

    @Test
    void testDeleteBook() {
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
        Mockito.when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        Mockito.doNothing().when(bookRepository).delete((Mockito.any()));
        bookService.remove(1);
        Mockito.verify(bookRepository).delete(Mockito.any());
    }

    @Test
    void testUpdateBook() {
        Mockito.when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);
        bookService.update(bookDTO);
        Mockito.verify(bookRepository).save(Mockito.any());
    }

    @Test
    void testGetAll() {
        List<BookDTO> bookDtos = new ArrayList<>();
        List<Book> books = new ArrayList<Book>();
        bookDtos.add(bookDTO);
        books.add(book);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(modelMapper.map(books, new TypeToken<List<BookDTO>>() {
        }.getType())).thenReturn(bookDtos);
        List<BookDTO> actual = bookService.getAll();
        Assertions.assertEquals(bookDtos.size(), actual.size());

    }








}
