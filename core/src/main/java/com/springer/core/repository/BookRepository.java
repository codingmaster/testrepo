package com.springer.core.repository;

import com.springer.core.domain.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book>
{
	Book findByAuthorAndTitle(String author, String title);
}
