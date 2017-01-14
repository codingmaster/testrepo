package com.springer.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Entity
public class Book extends Document
{
	@Column
	@Enumerated(EnumType.STRING)
	private List<Topic> topics;
	
	public List<Topic> getTopics()
	{
		return topics;
	}
	
	public void setTopics(List<Topic> topics)
	{
		this.topics = topics;
	}
}
