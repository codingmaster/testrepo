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

import javax.transaction.Transactional;
import java.util.concurrent.Future;

@Service
public class WatermarkService implements BaseService
{
	public static final long WATERMARKING_TIME = 30000L;
	
	private static final Logger LOG = LoggerFactory.getLogger(WatermarkService.class);
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private WatermarkRepository watermarkRepository;
	
	@Transactional
	public Ticket createTicket(Document document){
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
		
		LOG.info("Creating watermark for ticket : "  +ticket.getId());
		while(waitingTime < WATERMARKING_TIME){
			Thread.sleep(waitingStep);
			waitingTime+=waitingStep;
			long progress = Math.round((waitingTime / WATERMARKING_TIME) * 100);
			
			updateProgress(ticket, progress);
			LOG.info("Waiting for ticket "  +ticket.getId() + " progress " +  ticket.getProgress() + " status: " + ticket.getStatus());
		}
		
		ticket.setStatus(Status.DONE);
		
		LOG.info("Watermarking for " + ticket.getId() + " finished ms... status: " + ticket.getStatus());
		Watermark watermark = new Watermark();
		watermark.setDocument(ticket.getDocument());
		
		watermark = watermarkRepository.save(watermark);
		ticket = ticketRepository.save(ticket);
		
		return new AsyncResult<>(ticket);
	}
	
	@Transactional
	private void updateProgress(Ticket ticket, long progress)
	{
		ticket.setProgress(progress);
	}
}
