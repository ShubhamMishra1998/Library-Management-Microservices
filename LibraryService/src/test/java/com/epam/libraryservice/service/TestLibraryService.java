package com.epam.libraryservice.service;

import com.epam.libraryservice.clients.BookClient;
import com.epam.libraryservice.clients.UserClient;
import com.epam.libraryservice.dto.BookDto;
import com.epam.libraryservice.dto.LibraryDto;
import com.epam.libraryservice.dto.UserDto;
import com.epam.libraryservice.dto.UserWithBookDto;
import com.epam.libraryservice.entity.Library;
import com.epam.libraryservice.exception.BookException;
import com.epam.libraryservice.repository.LibraryRepository;
import com.epam.libraryservice.util.TestUtil;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.NoopCharAppender;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
   class TestLibraryService {
    @InjectMocks
    private LibraryServiceImpl libraryService;
    @MockBean
    private BookClient bookClient;
    @MockBean
    private UserClient userClient;
    @MockBean
    LibraryRepository repository;

    @MockBean
    ModelMapper modelMapper;

    TestUtil testUtil = new TestUtil();

    @Test
    void getBooksTest() {
        List<BookDto> books = testUtil.getBooks();
        when(bookClient.getAllBooks()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(books));
        assertEquals(books.size(), libraryService.getAllBooks().size());
    }

    @Test
    void getBookTest() {
        BookDto bookDto = testUtil.getBook();
        when(bookClient.getABook(1)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        BookDto actual = libraryService.getABook(1);
        Assertions.assertEquals(bookDto.getName(), actual.getName());

    }

    @Test
    void addBookTest() {
        BookDto bookDto = testUtil.getBook();
        when(bookClient.addABook(bookDto)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        BookDto actual = libraryService.addABook(bookDto);
        Assertions.assertEquals(bookDto.getName(), actual.getName());

    }

    @Test
    void updateBookTest() {
        BookDto bookDto = testUtil.getBook();
        when(bookClient.updateABook(1, bookDto)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        BookDto actual = libraryService.updateABook(1, bookDto);
        Assertions.assertEquals(bookDto.getName(), actual.getName());
    }

    @Test
    void deleteABook() {
        BookDto bookDto = testUtil.getBook();
        doNothing().when(repository).deleteByBookId(Mockito.anyInt());
        when(bookClient.deleteABook(Mockito.anyInt())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        libraryService.deleteABook(1);
        verify(bookClient).deleteABook(1);
    }

    @Test
    void getAllUsersTest() {
        List<UserDto> users = testUtil.getUsers();
        when(userClient.getAllUsers()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(users));
        assertEquals(users.size(), libraryService.getAllUsers().size());

    }

    @Test
    void addUserTest() {
        UserDto userDto = testUtil.getUsers().get(0);
        when(userClient.addAUser(userDto)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));
        UserDto actual = libraryService.addAUser(userDto);
        assertEquals(actual.getUserName(), userDto.getUserName());

    }

    @Test
    void updateUserTest() {
        UserDto userDto = testUtil.getUsers().get(0);
        when(userClient.updateAUser("shubham", userDto)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));
        UserDto actual = libraryService.updateAUser("shubham", userDto);
        assertEquals(actual.getUserName(), userDto.getUserName());
    }

    @Test
    void deleteUserTest() {
        UserDto userDto = testUtil.getUsers().get(0);
        doNothing().when(repository).deleteByUserName(Mockito.anyString());
        when(userClient.deleteAUser(Mockito.anyString())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));
        libraryService.deleteAUser("shubham");
        verify(userClient).deleteAUser("shubham");
    }

    @Test
    void issueBookTest() throws BookException {
        UserDto userDto = testUtil.getUsers().get(0);
        when(userClient.getAUser(Mockito.anyString())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));
        BookDto bookDto = testUtil.getBook();
        when(bookClient.getABook(Mockito.anyInt())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        List<Library> list = new ArrayList<>();
        Library library = getLibrary().get(0);
        LibraryDto libraryDto = getLibraryDto().get(0);
        Mockito.when(repository.findByUserName(Mockito.anyString())).thenReturn(list);
        Mockito.when(modelMapper.map(libraryDto, Library.class)).thenReturn(library);
        Mockito.when(repository.save(Mockito.any())).thenReturn(library);
        Mockito.when(modelMapper.map(library, LibraryDto.class)).thenReturn(libraryDto);
        LibraryDto actual = libraryService.issueBook("shubham", 2);

        Assertions.assertEquals(actual.getBookId(), libraryDto.getBookId());


    }


    private List<LibraryDto> getLibraryDto() {
        List<LibraryDto> list = new ArrayList<>();
        LibraryDto libraryDto = new LibraryDto(1, "shubham", 1);
        list.add(libraryDto);
        return list;
    }

    private List<Library> getLibrary() {
        List<Library> list = new ArrayList<>();
        Library library = new Library(1, "shubham", 1);
        Library library1 = new Library();
        library1.setBookId(1);
        library1.setId(1);
        library1.setUserName("shyam");
        list.add(library);
        list.add(library1);

        return list;
    }

    @Test
    void testIssueBookInvalid() throws BookException {
        List<Library> libraryList = new ArrayList<>();
        BookDto bookDto = testUtil.getBook();
        UserDto userDto = testUtil.getUsers().get(0);
        Library library = getLibrary().get(0);
        LibraryDto libraryDto = getLibraryDto().get(0);
        libraryList.add(library);
        Mockito.when(modelMapper.map(libraryDto, Library.class)).thenReturn(library);
        Mockito.when(modelMapper.map(library, LibraryDto.class)).thenReturn(libraryDto);
        Mockito.when(bookClient.getABook(Mockito.anyInt()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        Mockito.when(userClient.getAUser(Mockito.anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));

        Mockito.when(repository.findByUserNameAndBookId(Mockito.anyString(), Mockito.anyInt())).thenReturn(Optional.of(library));
        assertThrows(BookException.class, () -> libraryService.issueBook("vikky", 1));
    }


    @Test
    void testIssueBookLimit() throws BookException {
        List<Library> libraryList = new ArrayList<>();
        BookDto bookDto = testUtil.getBook();
        UserDto userDto = testUtil.getUsers().get(0);
        Library library = getLibrary().get(0);
        LibraryDto libraryDto = getLibraryDto().get(0);
        libraryList.add(library);
        Mockito.when(modelMapper.map(libraryDto, Library.class)).thenReturn(library);
        Mockito.when(modelMapper.map(library, LibraryDto.class)).thenReturn(libraryDto);
        Mockito.when(bookClient.getABook(Mockito.anyInt()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        Mockito.when(userClient.getAUser(Mockito.anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));
        Mockito.when(repository.findByUserNameAndBookId(Mockito.anyString(), Mockito.anyInt())).thenReturn(Optional.of(library));
        Mockito.when(repository.findByUserName(Mockito.anyString())).thenReturn(getLibrary());
        when(libraryService.fetchByUserName(Mockito.anyString())).thenReturn(getLibraryDto());


       assertThrows(BookException.class,()->libraryService.issueBook("abc",1));
    }


    @Test
    void testGetUser() {
        BookDto bookDto=testUtil.getBook();
        UserDto userDto=testUtil.getUsers().get(0);
        UserWithBookDto userBookDTO = new UserWithBookDto();
        List<Library> libraryList = new ArrayList<>();
        Library library=getLibrary().get(0);
        libraryList.add(library);
        Mockito.when(bookClient.getABook(Mockito.anyInt()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(bookDto));
        Mockito.when(userClient.getAUser(Mockito.anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userDto));
       when(repository.findByUserName(Mockito.anyString())).thenReturn(libraryList);
        when(libraryService.fetchByUserName(Mockito.anyString())).thenReturn(getLibraryDto());
        userBookDTO.setUserName(userDto.getUserName());
        userBookDTO.setEmail(userDto.getEmail());
        UserWithBookDto actual = libraryService.getAUser("shubham");
        assertEquals(userBookDTO.getName(), actual.getName());
    }

    @Test
    void testReleaseBook() throws BookException {
        when(repository.deleteByUserNameAndBookId(Mockito.anyString(),Mockito.anyInt())).thenReturn(1L);
        assertEquals("Book is released",libraryService.release(Mockito.anyString(),Mockito.anyInt()));
    }

//    @Test
//    void testGetPort(){
//        when(libraryService.getPort()).thenReturn(ResponseEntity.status(HttpStatus.OK).body("2004"));
//        assertEquals("200 OK",libraryService.getPort().getStatusCode().toString());
//    }











}




