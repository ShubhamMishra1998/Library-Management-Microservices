package com.epam.libraryservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Library {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	@Column(name = "user_name")

	private String userName;

	private int bookId;

}
