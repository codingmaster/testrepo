package com.springer.core.repository;

import com.springer.core.BaseTest;
import com.springer.core.domain.Book;
import com.springer.core.domain.Watermark;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class WatermarkRepositoryTest extends BaseTest
{
	
	@Autowired
	private WatermarkRepository watermarkRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void findByDocument() throws Exception
	{
		Book book = testDataSetup.createBook();
		book = bookRepository.save(book);
		
		assertThat(watermarkRepository.findByDocument(book), is(nullValue()));
		
		Watermark watermark = new Watermark();
		watermark.setDocument(book);
		
		watermarkRepository.save(watermark);
		assertThat(watermarkRepository.findByDocument(book), is(notNullValue()));
	}
	
}