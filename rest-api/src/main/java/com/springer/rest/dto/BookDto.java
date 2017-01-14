package com.springer.rest.dto;

import com.springer.core.domain.Book;
import com.springer.core.domain.Topic;

import java.util.List;

public class BookDto extends BaseDto<Book>
{
	private String author;
	private String title;
	private List<Topic> topics;
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public List<Topic> getTopics()
	{
		return topics;
	}
	
	public void setTopics(List<Topic> topics)
	{
		this.topics = topics;
	}
}
