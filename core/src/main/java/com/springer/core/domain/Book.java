package com.springer.core.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonDeserialize(as=Book.class)
public class Book extends Document
{
	
	@ElementCollection
	@JoinTable(name="book_topics", joinColumns = @JoinColumn(name="book_id"))
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
