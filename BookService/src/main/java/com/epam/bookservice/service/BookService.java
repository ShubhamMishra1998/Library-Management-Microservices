package com.epam.bookservice.service;

import com.epam.bookservice.dto.BookDTO;


import java.util.List;

public interface BookService {

     BookDTO add(BookDTO bookDto);
     void remove(int bookId);
     BookDTO get(int bookId);

    void update(BookDTO bookDto);
     List<BookDTO> getAll();


}
