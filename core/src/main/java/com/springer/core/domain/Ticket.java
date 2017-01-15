package com.springer.core.domain;

import javax.persistence.*;

@Entity
public class Ticket extends BaseEntity
{
	private Long progress;
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToOne
	private Document document;
	
	public Status getStatus()
	{
		return status;
	}
	
	public void setStatus(Status status)
	{
		this.status = status;
	}
	
	public Document getDocument()
	{
		return document;
	}
	
	public void setDocument(Document document)
	{
		this.document = document;
	}
	
	public void setProgress(Long progress)
	{
		this.progress = progress;
	}
	
	public Long getProgress()
	{
		return progress;
	}
}
