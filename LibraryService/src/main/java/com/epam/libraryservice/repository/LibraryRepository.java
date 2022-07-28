package com.epam.libraryservice.repository;


import com.epam.libraryservice.entity.Library;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends CrudRepository<Library, Long>{
	 List<Library> findByUserName(String userName);
	    Long deleteByUserNameAndBookId(String userName, int bookId);
	    void deleteByBookId(int bookId);
	    void deleteByUserName(String userName);
	Optional<Library> findByUserNameAndBookId(String userName, int bookId);
}
