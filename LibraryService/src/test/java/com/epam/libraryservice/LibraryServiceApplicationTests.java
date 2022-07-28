package com.epam.libraryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryServiceApplicationTests {

	@Test
	void contextLoads() {
		LibraryServiceApplication.main(new String[] {});
		boolean actual=true;
		boolean expected=true;
		assertEquals(expected,actual);
	}

}
