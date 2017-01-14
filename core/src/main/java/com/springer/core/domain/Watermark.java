package com.springer.core.domain;

import com.sun.istack.internal.NotNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Watermark extends BaseEntity
{
	@OneToOne
	@NotNull
	private Document document;
	
	public Document getDocument()
	{
		return document;
	}
	
	public void setDocument(Document document)
	{
		this.document = document;
	}
}
