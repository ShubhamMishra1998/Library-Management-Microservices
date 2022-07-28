package com.epam.bookservice.util;

import com.epam.bookservice.dto.BookDTO;
import com.epam.bookservice.entities.Book;

public class TestUtils {

    public static Book createBook() {
        Book book = new Book();
        book.setId(1);
        book.setName("abc");
        book.setAuthor("def");
        book.setPublisher("ghi");
        return book;
    }

    public static BookDTO createBookDto() {
        BookDTO bookDto = new BookDTO();
        bookDto.setId(1);
        bookDto.setName("abc");
        bookDto.setAuthor("def");
        bookDto.setPublisher("ghi");
        return bookDto;
    }

}
