package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.service.BaseService;
import com.springer.core.service.BookService;
import com.springer.rest.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/books")
public class BookApi extends BaseApi<Book, BookDto> {
	
	@Autowired
	private BookService bookService;
	
	@Override
	protected BaseService<Book> getService()
	{
		return bookService;
	}
	
	@Override
	protected Class<BookDto> getDtoType()
	{
		return BookDto.class;
	}
	
	@Override
	protected Book toDomain(BookDto dto)
	{
		Book book = new Book();
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());
		book.setTopics(dto.getTopics());
		return book;
	}
}
