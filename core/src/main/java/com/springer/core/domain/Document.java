package com.springer.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class Document extends BaseEntity
{
	@Column
	private String title;
	
	@Column
	private String author;
	
	@OneToOne
	private Watermark watermark;
	
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
	
	public Watermark getWatermark()
	{
		return watermark;
	}
	
	public void setWatermark(Watermark watermark)
	{
		this.watermark = watermark;
	}
}
