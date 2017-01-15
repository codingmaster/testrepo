package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.domain.Document;
import com.springer.core.domain.Journal;
import com.springer.core.domain.Watermark;
import com.springer.core.dto.WatermarkDto;
import com.springer.core.repository.BookRepository;
import com.springer.core.repository.DocumentRepository;
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
	DocumentRepository documentRepository;
	
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
		
		ResponseEntity<WatermarkDto> response = rest.getForEntity(BASE_URL + "/{documentId}", WatermarkDto.class, port, book.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		WatermarkDto watermarkDto = response.getBody();
		assertThat(watermarkDto.getTitle(), equalTo(book.getTitle()));
	}
	
	@Test
	public void createById() throws Exception
	{
		TestRestTemplate rest = new TestRestTemplate();
		Book book = testDataSetup.createBook();
		book = bookRepository.save(book);
		
		ResponseEntity<WatermarkDto> response = rest.postForEntity(BASE_URL + "/{documentId}", book, WatermarkDto.class, port, book.getId());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		WatermarkDto watermark = response.getBody();
		assertThat(watermark.getTitle(), equalTo(book.getTitle()));
		
		Journal journal = testDataSetup.createJournal();
		journalRepository.save(journal);
		
		response = rest.postForEntity(BASE_URL + "/{documentId}", journal, WatermarkDto.class, port, journal.getId());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		watermark = response.getBody();
		assertThat(watermark.getTitle(), equalTo(journal.getTitle()));
	}
	
	@Test
	public void createByDocument() throws Exception
	{
		TestRestTemplate rest = new TestRestTemplate();
		Book book = testDataSetup.createBook();
		book = documentRepository.save(book);
		ResponseEntity<WatermarkDto> response = rest.postForEntity(BASE_URL, book, WatermarkDto.class, port);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		WatermarkDto watermark = response.getBody();
		assertThat(watermark.getTitle(), equalTo(book.getTitle()));
		
		Journal journal = testDataSetup.createJournal();
		journal = journalRepository.save(journal);
		
		response = rest.postForEntity(BASE_URL, journal, WatermarkDto.class, port);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		watermark = response.getBody();
		assertThat(watermark.getTitle(), equalTo(journal.getTitle()));
	}
}