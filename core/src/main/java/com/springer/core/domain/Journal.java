package com.springer.core.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;

@Entity
@JsonDeserialize(as=Journal.class)
public class Journal extends Document
{
	public Journal(){
		super();
		setDtype("journal");
	}
}
