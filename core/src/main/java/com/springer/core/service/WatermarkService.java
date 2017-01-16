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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class WatermarkService implements BaseService
{
	public static final long WATERMARKING_TIME = 10000L;
	
	private static final Logger LOG = LoggerFactory.getLogger(WatermarkService.class);
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private WatermarkRepository watermarkRepository;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Transactional
	public Ticket createTicket(Document document)
	{
		Ticket ticket = new Ticket();
		ticket.setDocument(document);
		ticket.setStatus(Status.INITIATED);
		ticket.setProgress(0L);
		ticket = ticketRepository.save(ticket);
		return ticket;
	}
	
	@Async
	public void startWatermarking(Ticket ticket) throws InterruptedException
	{
		Document document = ticket.getDocument();
		ticket.setStatus(Status.PROCESSING);
		
		Double waitingTime = 0.0;
		Long waitingStep = 1000L;
		
		LOG.info("Creating watermark for ticket : " + ticket.getId());
		while (waitingTime < WATERMARKING_TIME)
		{
			Thread.sleep(waitingStep);
			waitingTime += waitingStep;
			long progress = Math.round((waitingTime / WATERMARKING_TIME) * 100);
			
			transactionTemplate.execute(transactionStatus -> {
				ticket.setProgress(progress);
				Ticket result = ticketRepository.save(ticket);
				LOG.info("WATERMARKING: for " + document.getId() + " " + result.getStatus() + " " + result.getProgress() + "%");
				return result;
			});
		}
		
		ticket.setStatus(Status.DONE);
		
		LOG.info("WATERMARKING: for " + document.getId() + " " + ticket.getStatus());
		Watermark watermark = new Watermark();
		watermark.setDocument(document);
		
		ticketRepository.save(ticket);
		watermarkRepository.save(watermark);
	}
	
	public Ticket executeWatermarking(Document document) throws InterruptedException
	{
		Ticket ticket = createTicket(document);
		startWatermarking(ticket);
		return ticket;
	}
}
