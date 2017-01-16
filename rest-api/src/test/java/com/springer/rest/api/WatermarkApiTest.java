package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.domain.Status;
import com.springer.core.domain.Watermark;
import com.springer.core.dto.TicketDto;
import com.springer.core.dto.WatermarkDto;
import com.springer.core.repository.BookRepository;
import com.springer.core.repository.DocumentRepository;
import com.springer.core.repository.JournalRepository;
import com.springer.core.repository.WatermarkRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.springer.core.service.WatermarkService.WATERMARKING_TIME;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class WatermarkApiTest extends BaseApiTest
{
	private final String WATERMARK_URL = "http://localhost:{port}/api/v1/watermarks";
	private final String TICKET_URL = "http://localhost:{port}/api/v1/tickets";
	
	private static final Logger LOG = LoggerFactory.getLogger(WatermarkApiTest.class);
	
	
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
		
		ResponseEntity<WatermarkDto> response = rest.getForEntity(WATERMARK_URL + "/{documentId}", WatermarkDto.class, port, book.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		WatermarkDto watermarkDto = response.getBody();
		assertThat(watermarkDto.getTitle(), equalTo(book.getTitle()));
	}


	@Test
	public void createWatermarkAsynchronously() throws Exception
	{
		TestRestTemplate rest = new TestRestTemplate();
		Book book = testDataSetup.createBook();
		book = documentRepository.save(book);
		ResponseEntity<TicketDto> response = rest.postForEntity(WATERMARK_URL, book, TicketDto.class, port);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		TicketDto ticket = response.getBody();
		assertThat(ticket.getStatus(), anyOf(equalTo(Status.INITIATED),equalTo(Status.PROCESSING)));
		
		double maxWaitingTime = WATERMARKING_TIME * 0.9;
		Long waitingTime = 0L;
		Long waitingStep = 1000L;
		
		ResponseEntity<WatermarkDto> watermarkResponse = rest.getForEntity(WATERMARK_URL + "/{documentId}", WatermarkDto.class, port, book.getId());
		assertEquals(HttpStatus.OK, watermarkResponse.getStatusCode());
		assertThat(watermarkResponse.getBody(), is(nullValue()));
		
		LOG.info("TEST: Creating watermark... status: "  +ticket.getStatus());
		while(waitingTime < maxWaitingTime){
			Thread.sleep(waitingStep);
			waitingTime+=waitingStep;
			
			response = rest.getForEntity(TICKET_URL  + "/{id}", TicketDto.class, port, ticket.getId());
			assertEquals(HttpStatus.OK, response.getStatusCode());
			ticket = response.getBody();
			assertThat(ticket.getStatus(), equalTo(Status.PROCESSING));
			LOG.info("TEST: Waiting " + waitingTime + "ms / " + maxWaitingTime + "ms... status: " + ticket.getStatus() + " " + ticket.getProgress() + "%");
		}
		Thread.sleep(waitingStep*3);
		watermarkResponse = rest.getForEntity(WATERMARK_URL + "/{documentId}", WatermarkDto.class, port, book.getId());
		assertEquals(HttpStatus.OK, watermarkResponse.getStatusCode());
		assertThat(watermarkResponse.getBody(), is(notNullValue()));
		
		response = rest.getForEntity(TICKET_URL  + "/{id}", TicketDto.class, port, ticket.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ticket = response.getBody();
		
		assertThat(ticket.getStatus(), equalTo(Status.DONE));
	}
}