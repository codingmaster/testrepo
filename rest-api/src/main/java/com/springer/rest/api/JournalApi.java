package com.springer.rest.api;

import com.springer.core.domain.Journal;
import com.springer.core.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/journals")
public class JournalApi extends BaseApi {
	
	@Autowired
	private JournalRepository journalRepository;
	
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Journal create(@RequestBody Journal journal) {
		journal = journalRepository.save(journal);
		return journal;
	}
	
	@Transactional
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable long id, @RequestBody Journal journal) {
		journalRepository.save(journal);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Journal get(@PathVariable long id) throws InstantiationException, IllegalAccessException {
		return journalRepository.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Journal> getAll(){
		return journalRepository.findAll();
	}
	
	@Transactional
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable long id) {
		journalRepository.delete(id);
	}
}
