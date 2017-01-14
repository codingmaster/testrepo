package com.springer.core.domain;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !Objects.equals(getClass(), o.getClass())) return false;
		BaseEntity that = (BaseEntity) o;
		return Objects.equals(getId(), that.getId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
