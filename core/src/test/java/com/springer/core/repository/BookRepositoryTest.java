package com.springer.core.repository;

import com.springer.core.BaseTest;
import com.springer.core.domain.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookRepositoryTest extends BaseTest
{
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void testFindBookByAuthorAndTitle() throws Exception
	{
		Book book = bookRepository.save(testDataSetup.createBook());
		Book actual = bookRepository.findByAuthorAndTitle(book.getAuthor(), book.getTitle());
		assertThat(actual, equalTo(book));
	}
}
