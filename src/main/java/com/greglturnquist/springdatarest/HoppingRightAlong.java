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

import static org.springframework.hateoas.client.Hop.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greglturnquist.springdatarest.jpa.Book;
import com.greglturnquist.springdatarest.mongodb.Album;

/**
 * @author Greg Turnquist
 */
@Controller
public class HoppingRightAlong {

	private static final Logger log = LoggerFactory.getLogger(HoppingRightAlong.class);

	private final ParameterizedTypeReference<Resource<Album>> albumType = new ParameterizedTypeReference<Resource<Album>>() {};
	private final ParameterizedTypeReference<Resource<Book>> bookType = new ParameterizedTypeReference<Resource<Book>>() {};
	private final Traverson traverson;

	public HoppingRightAlong() throws URISyntaxException {
		traverson = new Traverson(new URI("http://localhost:8080/api/"), MediaTypes.HAL_JSON);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/traverson/book/{isbn}")
	public @ResponseBody Resource<Book> hopToBook(@PathVariable String isbn) throws URISyntaxException {

		Resource<Book> book = traverson
				.follow("books", "search")
				.follow(rel("findByIsbn").withParameter("isbn", isbn))
				.toObject(bookType);

		log.info(book.getContent().toString());

		return book;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/traverson/album/{isbn}")
	public @ResponseBody Resource<Album> hopToAlbum(@PathVariable String isbn) throws URISyntaxException {

		Resource<Album> album = traverson
				.follow("albums", "search")
				.follow(rel("findByIsbn").withParameter("isbn", isbn))
				.toObject(albumType);

		log.info(album.getContent().toString());

		return album;
	}
}
