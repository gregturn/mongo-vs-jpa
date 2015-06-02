/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.springdatarest;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.greglturnquist.springdatarest.jpa.Book;
import com.greglturnquist.springdatarest.jpa.BookRepository;
import com.greglturnquist.springdatarest.mongodb.Album;
import com.greglturnquist.springdatarest.mongodb.AlbumRepository;

/**
 * @author Greg Turnquist
 */
@Component
public class DatabaseLoader {

	private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

	private final BookRepository bookRepository;
	private final AlbumRepository albumRepository;

	@Autowired
	public DatabaseLoader(BookRepository bookRepository, AlbumRepository albumRepository) {

		this.bookRepository = bookRepository;
		this.albumRepository = albumRepository;
	}

	@PostConstruct
	public void init() {

		bookRepository.deleteAll();
		albumRepository.deleteAll();

		bookRepository.save(new Book("123", "Learning Spring Boot"));
		bookRepository.save(new Book("456", "Python Testing Cookbook"));
		bookRepository.save(new Book("789", "Spring Python 1.1"));

		albumRepository.save(new Album("123", "Spring in Action"));
		albumRepository.save(new Album("456", "Spring in Action 2nd Edition"));
		albumRepository.save(new Album("789", "Spring in Action 3rd Edition"));

		for (Book book : bookRepository.findAll()) {
			log.info("Found " + book);
		}

		for (Album album : albumRepository.findAll()) {
			log.info("Found " + album);
		}
	}

}
