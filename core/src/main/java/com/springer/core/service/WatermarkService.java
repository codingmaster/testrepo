package com.springer.core.service;

import com.springer.core.domain.Document;
import com.springer.core.domain.Status;
import com.springer.core.domain.Ticket;
import com.springer.core.domain.Watermark;
import com.springer.core.repository.TicketRepository;
import com.springer.core.repository.WatermarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

@Service
public class WatermarkService implements BaseService
{
	public static final long WATERMARKING_TIME = 10000L;
	
	private static final Logger LOG = LoggerFactory.getLogger(WatermarkService.class);
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private WatermarkRepository watermarkRepository;
	
	@Transactional
	public Ticket createTicket(Document document)
	{
		Ticket ticket = new Ticket();
		ticket.setDocument(document);
		ticket.setStatus(Status.INITIATED);
		ticket = ticketRepository.save(ticket);
		return ticket;
	}
	
	@Async
	public Future<Ticket> startWatermarking(Ticket ticket) throws InterruptedException
	{
		ticket.setStatus(Status.PROCESSING);
		
		Double waitingTime = 0.0;
		Long waitingStep = 1000L;
		
		LOG.info("Creating watermark for ticket : " + ticket.getId());
		while (waitingTime < WATERMARKING_TIME)
		{
			Thread.sleep(waitingStep);
			waitingTime += waitingStep;
			long progress = Math.round((waitingTime / WATERMARKING_TIME) * 100);
			
			ticket.setProgress(progress);
			LOG.info("Waiting for ticket " + ticket.getDocument().getId() + " " + ticket.getStatus() + " " + ticket.getProgress() + "%");
		}
		
		ticket.setStatus(Status.DONE);
		
		LOG.info("Watermarking for " + ticket.getDocument().getId() + " finished ms... status: " + ticket.getStatus());
		Watermark watermark = new Watermark();
		watermark.setDocument(ticket.getDocument());
		
		ticket = ticketRepository.save(ticket);
		watermark = watermarkRepository.save(watermark);
		
		return new AsyncResult<>(ticket);
	}
	
}
