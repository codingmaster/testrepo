package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.domain.Topic;
import com.springer.core.repository.BookRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class BooksApiTest extends BaseApiTest
{
	private final String BASE_URL = "http://localhost:{port}/api/v1/books";
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void canGetAll() {
		bookRepository.save(testDataSetup.createBook());
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<List> response = rest.getForEntity(BASE_URL, List.class, port);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List items = response.getBody();
		assertEquals(items.size(), bookRepository.findAll().size());
	}
	
	@Test
	public void createBook() throws Exception
	{
		String author = "My author";
		String title = "My title";
		Book book = testDataSetup.createBook(author, title);
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Book> response = rest.postForEntity(BASE_URL, book, Book.class, port);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertThat(bookRepository.findByAuthorAndTitle(author, title), is(notNullValue()));
	}
	
	@Test
	public void updateBook() throws Exception
	{
		String author = "My author";
		String title = "My title";
		Book book = testDataSetup.createBook(author, title);
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Book> response = rest.postForEntity(BASE_URL, book, Book.class, port);
		book = response.getBody();
		title = "Another title";
		book.setTitle(title);
		
		rest.put(BASE_URL + "/{id}", book, port, book.getId());
		response = rest.getForEntity(BASE_URL + "/{id}", Book.class, port, book.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody().getTitle(), equalTo(title));
	}
	
	@Test
	public void deleteBook() throws Exception
	{
		Book book = testDataSetup.createBook();
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Book> response = rest.postForEntity(BASE_URL, book, Book.class, port);
		book = response.getBody();
		
		rest.delete(BASE_URL + "/{id}", port, book.getId());
		response = rest.getForEntity(BASE_URL + "/{id}", Book.class, port, book.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody(), is(nullValue()));
	}
}
