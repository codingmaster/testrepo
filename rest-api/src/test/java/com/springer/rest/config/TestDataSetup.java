package com.springer.rest.config;

import com.springer.core.domain.Book;
import com.springer.core.domain.Journal;
import com.springer.core.domain.Topic;
import org.springframework.stereotype.Component;

@Component
public class TestDataSetup
{
	public Book createBook(){
		return createBook("Test Author", "Test Book Title");
	}
	
	public Book createBook(String author, String title){
		Book book = new Book();
		book.setAuthor(author);
		book.setTitle(title);
		book.setDtype("book");
		book.setTopic(Topic.BUSINESS);
		return book;
	}
	
	public Journal createJournal()
	{
		return createJournal("Test Author", "Test Journal Title");
	}
	
	public Journal createJournal(String author, String title)
	{
		Journal journal = new Journal();
		journal.setTitle(title);
		journal.setAuthor(author);
		journal.setDtype("journal");
		return journal;
	}
}
