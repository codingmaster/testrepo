package com.springer.rest.api;

import com.springer.core.domain.BaseEntity;
import com.springer.core.service.BaseService;
import com.springer.rest.dto.BaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;

@RestController
public abstract class BaseApi<Domain extends BaseEntity, Dto extends BaseDto<Domain>> {
	private static final Logger logger = LoggerFactory.getLogger(BaseApi.class);
	
	protected abstract BaseService<Domain> getService();
	
	protected abstract Class<Dto> getDtoType();
	
	protected abstract Domain toDomain(Dto dto);
	
	
	@PostConstruct
	public void init() {
		logger.info("Bean created: " + getClass().getName());
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Dto create(@RequestBody Dto dto) {
		logger.info("create() called for " + dto.getClass().getSimpleName());
		Class<Dto> clazz = getDtoType();
		Domain domain = getService().create(toDomain(dto));
		try {
			return clazz.getDeclaredConstructor(domain.getClass()).newInstance(domain);
		} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable long id, @RequestBody Dto dto) {
		logger.info("update() called for " + dto.getClass().getSimpleName() + " with id " + id);
		getService().update(toDomain(dto));
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Dto get(@PathVariable long id) throws InstantiationException, IllegalAccessException {
		logger.info("get() called for id " + id);
		Domain domain = getService().getById(id);
		Class<Dto> clazz = getDtoType();
		try {
			return clazz.getDeclaredConstructor(domain.getClass()).newInstance(domain);
		} catch (InvocationTargetException | NoSuchMethodException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable long id) {
		logger.info("delete() called for " + id);
		getService().delete(id);
	}
}
