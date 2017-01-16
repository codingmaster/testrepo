package com.springer.core.service;

import com.springer.core.BaseTest;
import com.springer.core.domain.Book;
import com.springer.core.domain.Status;
import com.springer.core.domain.Ticket;
import com.springer.core.repository.BookRepository;
import com.springer.core.repository.TicketRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

import static com.springer.core.service.WatermarkService.WATERMARKING_TIME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WatermarkServiceTest extends BaseTest
{
	private static final Logger LOG = LoggerFactory.getLogger(WatermarkServiceTest.class);
	
	@Autowired
	private WatermarkService watermarkService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Test
	public void startWatermarking() throws Exception
	{
		Book book = testDataSetup.createBook();
		book = bookRepository.save(book);
		
		Ticket ticket = watermarkService.createTicket(book);
		final Future<Ticket> future = watermarkService.startWatermarking(ticket);
		
		double maxWaitingTime = WATERMARKING_TIME * 0.9;
		Long waitingTime = 0L;
		Long waitingStep = 1000L;
		
		LOG.info("TEST: Creating watermark... status: " + ticket.getStatus());
		
		while(waitingTime < maxWaitingTime){
			Thread.sleep(waitingStep);
			waitingTime+=waitingStep;
			ticket = ticketRepository.findOne(ticket.getId());
			
			assertThat(ticket.getStatus(), equalTo(Status.PROCESSING));
			LOG.info("TEST: Waiting " + waitingTime + "ms / " + maxWaitingTime + "ms... status: " + ticket.getStatus() + " " + ticket.getProgress() + "%");
		}
		
		while(!future.isDone()){
			Thread.sleep(1000L);
			LOG.info("TEST: Waiting for done");
		}
		
		ticket = ticketRepository.findOne(ticket.getId());
		
		assertThat(ticket.getStatus(), equalTo(Status.DONE));
		LOG.info("TEST: Waiting finished status: " + ticket.getStatus());
	}
	
}