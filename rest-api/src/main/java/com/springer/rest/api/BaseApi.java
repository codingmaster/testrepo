package com.springer.rest.api;

import com.springer.core.domain.Book;
import com.springer.core.domain.Document;
import com.springer.core.domain.Journal;
import com.springer.core.domain.Topic;
import com.springer.core.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public abstract class BaseApi
{
	@Autowired
	DocumentRepository documentRepository;
	private static final long MAX_DOCUMENTS = 10;
	
	@PostConstruct
	@Transactional
	public void init() {
		if(documentRepository.count() == 0){
			
			while (documentRepository.count() < MAX_DOCUMENTS){
				List<Document> documents = new ArrayList<>();
				Book book1 = new Book();
				book1.setTitle("The Dark Code");
				book1.setAuthor("Bruce Wayne");
				book1.setTopic(Topic.SCIENCE);
				
				documents.add(book1);
				
				Book book2 = new Book();
				book2.setTitle("How to make money");
				book2.setAuthor("Dr. Evil");
				book2.setTopic(Topic.BUSINESS);
				
				documents.add(book2);
				
				Journal journal = new Journal();
				journal.setAuthor("Clark Kent");
				journal.setTitle("Journal of human flight routes");
				documents.add(journal);
				
				documentRepository.save(documents);
			}
		}
	}
}
