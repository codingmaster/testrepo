package com.springer.core.dto;

import com.springer.core.domain.Status;
import com.springer.core.domain.Ticket;

public class TicketDto
{
	private Long id;
	private Long progress;
	private Status status;
	
	public TicketDto(Ticket ticket)
	{
		this.id = ticket.getId();
		this.status = ticket.getStatus();
		this.progress = ticket.getProgress();
		
	}
	
	public TicketDto()
	{
		
	}
	
	public Long getId()
	{
		return id;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public Long getProgress()
	{
		return progress;
	}
}
