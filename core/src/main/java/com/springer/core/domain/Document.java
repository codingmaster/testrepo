package com.springer.core.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.springer.core.util.DocumentDeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;

@JsonDeserialize(using=DocumentDeSerializer.class)
@Entity
public abstract class Document extends BaseEntity
{
	@Column(insertable = false, updatable = false)
	private String dtype;
	
	@Column
	private String title;
	
	@Column
	private String author;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "watermark_id")
//	@NotFound(action = NotFoundAction.IGNORE)
//	private Watermark watermark;
	
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
	
//	public Watermark getWatermark()
//	{
//		return watermark;
//	}
//
//	public void setWatermark(Watermark watermark)
//	{
//		this.watermark = watermark;
//	}
	
	public String getDtype()
	{
		return dtype;
	}
	
	public void setDtype(String dtype)
	{
		this.dtype = dtype;
	}
}
