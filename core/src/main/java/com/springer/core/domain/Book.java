package com.springer.core.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@JsonDeserialize(as=Book.class)
public class Book extends Document
{
	@Column
	@Enumerated(EnumType.STRING)
	private Topic topic;
	
	public Topic getTopic()
	{
		return topic;
	}
	
	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}
	
}
