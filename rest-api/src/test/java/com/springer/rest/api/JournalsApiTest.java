package com.springer.rest.api;

import com.springer.core.domain.Journal;
import com.springer.core.repository.JournalRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class JournalsApiTest extends BaseApiTest
{
	private final String BASE_URL = "http://localhost:{port}/api/v1/journals";
	
	@Autowired
	private JournalRepository journalRepository;
	
	@Test
	public void canGetAll() {
		journalRepository.save(testDataSetup.createJournal());
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<List> response = rest.getForEntity(BASE_URL, List.class, port);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List items = response.getBody();
		assertEquals(items.size(), journalRepository.findAll().size());
	}
	
	@Test
	public void createJournal() throws Exception
	{
		String author = "My author";
		String title = "My title";
		Journal journal = testDataSetup.createJournal(author, title);
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Journal> response = rest.postForEntity(BASE_URL, journal, Journal.class, port);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertThat(journalRepository.findByAuthorAndTitle(author, title), is(notNullValue()));
	}
	
	@Test
	public void updateJournal() throws Exception
	{
		String author = "My author";
		String title = "My title";
		Journal journal = testDataSetup.createJournal(author, title);
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Journal> response = rest.postForEntity(BASE_URL, journal, Journal.class, port);
		journal = response.getBody();
		title = "Another title";
		journal.setTitle(title);
		
		rest.put(BASE_URL + "/{id}", journal, port, journal.getId());
		response = rest.getForEntity(BASE_URL + "/{id}", Journal.class, port, journal.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody().getTitle(), equalTo(title));
	}
	
	@Test
	public void deleteJournal() throws Exception
	{
		Journal journal = testDataSetup.createJournal();
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Journal> response = rest.postForEntity(BASE_URL, journal, Journal.class, port);
		journal = response.getBody();
		
		rest.delete(BASE_URL + "/{id}", port, journal.getId());
		response = rest.getForEntity(BASE_URL + "/{id}", Journal.class, port, journal.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody(), is(nullValue()));
	}
}
