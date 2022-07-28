package com.epam.libraryservice.service;

import com.epam.libraryservice.clients.BookClient;
import com.epam.libraryservice.clients.UserClient;
import com.epam.libraryservice.dto.*;
import com.epam.libraryservice.exception.BookException;
import com.epam.libraryservice.entity.Library;
import com.epam.libraryservice.repository.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

	private static final int BOOKLIMIT=3;
	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	private UserClient userClient;
	@Autowired
	private BookClient bookClient;
	@Autowired
	ModelMapper modelMapper;


	public List<BookDto> getAllBooks() {
		return Optional.ofNullable(bookClient.getAllBooks()).map(ResponseEntity::getBody).orElseGet(ArrayList::new);

	}

	public BookDto getABook(int bookId) {
		return Optional.ofNullable(bookClient.getABook(bookId)).map(ResponseEntity::getBody).orElseGet(BookDto::new);
	}

	public BookDto addABook(BookDto bookDto) {
		return Optional.ofNullable(bookClient.addABook(bookDto)).map(ResponseEntity::getBody).orElseGet(BookDto::new);
	}

	public BookDto updateABook(int bookId, BookDto bookDto) {
		return Optional.ofNullable(bookClient.updateABook(bookId,bookDto)).map(ResponseEntity::getBody).orElseGet(BookDto::new);
	}

	public void deleteABook(int bookId) {
		this.release(bookId);
		bookClient.deleteABook(bookId);
	}

	public List<UserDto> getAllUsers() {
		return Optional.ofNullable(userClient.getAllUsers()).map(ResponseEntity::getBody).orElseGet(ArrayList::new);

	}

	public UserWithBookDto getAUser(String userName) {


		UserDto userDto= Optional.ofNullable(userClient.getAUser(userName)).map(ResponseEntity::getBody).orElseGet(UserDto::new);
		UserWithBookDto userServiceDto = new UserWithBookDto();
		modelMapper.map(userDto, userServiceDto);
		List<BookDto> books = new ArrayList<>();
		fetchByUserName(userName).forEach(library -> books
				.add(bookClient.getABook(library.getBookId()).getBody()));
		userServiceDto.setBooks(books);
		return userServiceDto;

	}



	public UserDto addAUser(UserDto userDto) {
		return Optional.ofNullable(userClient.addAUser(userDto)).map(ResponseEntity::getBody).orElseGet(UserDto::new);
	}

	public UserDto updateAUser(String userName, UserDto userDto) {

		return Optional.ofNullable(userClient.updateAUser(userName,userDto)).map(ResponseEntity::getBody).orElseGet(UserDto::new);
	}

	public LibraryDto issueBook(String userName, int bookId) throws BookException {

		if(libraryRepository.findByUserNameAndBookId(userName,bookId).isPresent()) {
			throw new BookException("This book is already issued to "+userName);
		}
		Optional.of(hasUserReachedMaxRange(userName)).filter(e->!e).map(i->"Book is issued")
				.orElseThrow(()-> new BookException("Please return some books to get more"));
		BookDto book = Optional.ofNullable(bookClient.getABook(bookId).getBody()).orElseGet(BookDto::new);
		UserDto user = Optional.ofNullable(userClient.getAUser(userName).getBody()).orElseGet(UserDto::new);

		LibraryDto libraryDto=LibraryDto.builder().userName(user.getUserName()).bookId(book.getId()).build();

		return modelMapper.map(libraryRepository.save(modelMapper.map(libraryDto,Library.class)),LibraryDto.class);

	}


	public boolean hasUserReachedMaxRange(String userName) {
		return libraryRepository.findByUserName(userName).size() == BOOKLIMIT;
	}

	public String release(String userName, int bookId) throws BookException {

		return Optional.ofNullable(libraryRepository.deleteByUserNameAndBookId(userName, bookId))
				.filter(i->i==1)
				.map(i->"Book is released")
				.orElseThrow(()-> new BookException("No record found for user:" + userName + " and book:" + bookId));
	}

	public void deleteAUser(String userName) {
		this.release(userName);
		userClient.deleteAUser(userName).getStatusCode();
	}

	public void release(String userName) {
		libraryRepository.deleteByUserName(userName);
	}

	public void release(int bookId) {
		libraryRepository.deleteByBookId(bookId);

	}

	public List<LibraryDto> fetchByUserName(String userName) {
		return modelMapper.map(libraryRepository.findByUserName(userName), new TypeToken<List<LibraryDto>>() {
		}.getType());

	}



}
