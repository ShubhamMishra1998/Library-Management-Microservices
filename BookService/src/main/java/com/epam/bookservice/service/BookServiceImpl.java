package com.epam.bookservice.service;

import com.epam.bookservice.dto.BookDTO;
import com.epam.bookservice.entities.Book;
import com.epam.bookservice.exceptions.ResourceNotFoundException;
import com.epam.bookservice.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public BookDTO add(BookDTO bookDto) {
        return modelMapper.map(bookRepository.save(modelMapper.map(bookDto, Book.class)), BookDTO.class);
    }

    @Override
    @Transactional
    public void remove(int bookId) {
        bookRepository.delete(modelMapper.map(get(bookId), Book.class));
    }

    @Override
    public BookDTO get(int bookId) {
        return modelMapper.map(
                bookRepository.findById(bookId).orElseThrow(
                        () -> new ResourceNotFoundException("Book with bookId:" + bookId + " does not exist")),
                BookDTO.class);

    }

    @Override
    public void update(BookDTO bookDto) {
        bookRepository.save(modelMapper.map(bookDto, Book.class));
    }

    @Override
    public List<BookDTO> getAll() {
        return modelMapper.map((List<Book>) bookRepository.findAll(), new TypeToken<List<BookDTO>>() {
        }.getType());

    }
}
