package com.epam.libraryservice.util;

import com.epam.libraryservice.dto.BookDto;
import com.epam.libraryservice.dto.UserDto;
import com.epam.libraryservice.dto.UserWithBookDto;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {


    public List<BookDto> getBooks(){
       List<BookDto> bookDtos=new ArrayList<>();
       BookDto b1=new BookDto();
       b1.setId(1);
       b1.setAuthor("def");
       b1.setPublisher("abc");
       b1.setName("xyz");
        BookDto b2= new BookDto();
        b2.setId(2);
        b1.setAuthor("defa");
        b1.setPublisher("abca");
        b1.setName("xyza");
       bookDtos.add(b1);
       bookDtos.add(b2);
       return  bookDtos;
    }

    public BookDto getBook(){
        BookDto b1=new BookDto();
        b1.setId(1);
        b1.setAuthor("def");
        b1.setPublisher("abc");
        b1.setName("xyz");
        return b1;
    }

    public List<UserDto> getUsers(){
        List<UserDto> users=new ArrayList<>();
        UserDto u1=new UserDto(1,"shubham","sm544735@gmail.com","shubham");
        users.add(u1);
        return users;

    }

    public UserWithBookDto getUser(){
        return new UserWithBookDto(1,"shubham","sm544735@gmail.com","shubham",getBooks());

    }




}
