package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.domain.Journal;
import com.springer.core.domain.Watermark;
import com.springer.core.repository.BookRepository;
import com.springer.core.repository.JournalRepository;
import com.springer.core.repository.WatermarkRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class WatermarkApiTest extends BaseApiTest
{
	private final String BASE_URL = "http://localhost:{port}/api/v1/watermarks";
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	JournalRepository journalRepository;
	
	@Autowired
	WatermarkRepository watermarkRepository;

	@Test
	public void get() throws Exception
	{
		TestRestTemplate rest = new TestRestTemplate();
		Book book = testDataSetup.createBook();
		book = bookRepository.save(book);
		
		Watermark watermark = new Watermark();
		watermark.setDocument(book);
		
		watermarkRepository.save(watermark);
		
		ResponseEntity<Watermark> response = rest.getForEntity(BASE_URL + "/{documentId}", Watermark.class, port, book.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		watermark = response.getBody();
		assertThat(watermark.getDocument(), equalTo(book));
	}
	
	@Test
	public void create() throws Exception
	{
		TestRestTemplate rest = new TestRestTemplate();
		Book book = testDataSetup.createBook();
		book = bookRepository.save(book);
		
		ResponseEntity<Watermark> response = rest.postForEntity(BASE_URL + "/{documentId}", book, Watermark.class, port, book.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Watermark watermark = response.getBody();
		assertThat(watermark.getDocument(), equalTo(book));
		
		Journal journal = testDataSetup.createJournal();
		journalRepository.save(journal);
		
		response = rest.postForEntity(BASE_URL + "/{documentId}", journal, Watermark.class, port, journal.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		watermark = response.getBody();
		assertThat(watermark.getDocument(), equalTo(journal));
	}
}