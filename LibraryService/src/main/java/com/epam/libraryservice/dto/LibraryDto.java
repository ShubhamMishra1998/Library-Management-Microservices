package com.epam.libraryservice.dto;

import lombok.*;

@Getter
//@Setter
//@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class LibraryDto {
	private int id;

	private String userName;

	private int bookId;

	public LibraryDto(int id, String userName, int bookId) {
		this.id = id;
		this.userName = userName;
		this.bookId = bookId;
	}

}
