package com.springer.core.repository;

import com.springer.core.BaseTest;
import com.springer.core.domain.Book;
import com.springer.core.domain.Topic;
import org.assertj.core.util.Lists;
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
		Book book = new Book();
		book.setAuthor("Test Author");
		book.setTitle("Test Title");
		book.setTopics(Lists.newArrayList(Topic.BUSINESS));
		book = bookRepository.save(book);
		Book actual = bookRepository.findByAuthorAndTitle(book.getAuthor(), book.getTitle());
		assertThat(actual, equalTo(book));
	}
}
