package com.springer.core.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
public abstract class Document extends BaseEntity
{
	@Column
	private String title;
	
	@Column
	private String author;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "watermark_id")
	@NotFound(action = NotFoundAction.IGNORE)
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
