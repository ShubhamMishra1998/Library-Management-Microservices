package com.epam.eurekaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EurekaServerApplicationTests {

	@Test
	void contextLoads() {

		boolean actual=true;
		boolean expected=true;
		assertEquals(expected,actual);
	}

}
