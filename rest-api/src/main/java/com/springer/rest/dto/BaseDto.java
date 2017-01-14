package com.springer.rest.dto;

import com.springer.core.domain.BaseEntity;

import java.io.Serializable;

public class BaseDto<Domain extends BaseEntity> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public BaseDto() {}
	
	public BaseDto(Domain domain) {}
	
}
