package com.springer.core.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Watermark extends BaseEntity
{
	@OneToOne(optional = false)
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
