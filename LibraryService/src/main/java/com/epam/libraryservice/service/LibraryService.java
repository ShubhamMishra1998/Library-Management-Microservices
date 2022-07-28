package com.epam.libraryservice.service;

import com.epam.libraryservice.dto.*;
import com.epam.libraryservice.exception.BookException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibraryService {
	public List<BookDto> getAllBooks();
	public BookDto getABook(int bookId);
	public BookDto addABook(BookDto bookDto);
	public BookDto updateABook(int bookId, BookDto bookDto);
	public void deleteABook(int bookId);
	public List<UserDto> getAllUsers() ;
	public UserWithBookDto getAUser(String userName);
	public UserDto addAUser(UserDto userDto);
	public UserDto updateAUser(String userName, UserDto userDto);
	public LibraryDto issueBook(String userName, int bookId) throws BookException;
	public boolean hasUserReachedMaxRange(String userName);
	public String release(String userName, int bookId) throws BookException;
	public void deleteAUser(String userName);
	public void release(String userName);
	public void release(int bookId);


	
}
