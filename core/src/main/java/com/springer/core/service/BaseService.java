package com.springer.core.service;

import com.springer.core.domain.BaseEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface BaseService<Domain extends BaseEntity> {
	@Transactional
	Domain create(Domain domain);
	
	@Transactional
	Domain update(Domain domain);
	
	@Transactional
	void delete(long id);
	
	Domain getById(long id);
	
	List<Domain> getAll();
	
	/**
	 * Returns all entities newer than the date.
	 *
	 * @param time (optional)
	 * @return
	 */
	List<Domain> getAll(LocalDateTime time);
	
}
