package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookApi extends BaseApi {
	
	@Autowired
	private BookRepository bookRepository;
	
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Book create(@RequestBody Book book) {
		book = bookRepository.save(book);
		return book;
	}
	
	@Transactional
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable long id, @RequestBody Book book) {
		bookRepository.save(book);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Book get(@PathVariable long id) throws InstantiationException, IllegalAccessException {
		return bookRepository.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Book> getAll(){
		return bookRepository.findAll();
	}
	
	@Transactional
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable long id) {
		bookRepository.delete(id);
	}
}
