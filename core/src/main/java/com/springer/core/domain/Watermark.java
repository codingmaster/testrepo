package com.springer.core.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Watermark extends BaseEntity
{
	
	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
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
