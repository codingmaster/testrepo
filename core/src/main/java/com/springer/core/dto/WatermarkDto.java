package com.springer.core.dto;

import com.springer.core.domain.Book;
import com.springer.core.domain.Document;
import com.springer.core.domain.Journal;
import com.springer.core.domain.Watermark;

public class WatermarkDto
{
	private String content;
	private String title;
	private String author;
	private String topic;
	
	public WatermarkDto(Watermark watermark){
		Document document = watermark.getDocument();
		
		this.title = document.getTitle();
		this.author = document.getAuthor();
		if(document instanceof Book){
			Book book = (Book) document;
			this.content = "book";
			this.topic = book.getTopic().name();
		}
		else if(document instanceof Journal){
			this.content = "journal";
		}
	}
	
	public WatermarkDto()
	{
		
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
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
	
	public String getTopic()
	{
		return topic;
	}
	
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
}
