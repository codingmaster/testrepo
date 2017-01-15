package com.springer.core.repository;

import com.springer.core.domain.Journal;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends BaseRepository<Journal>
{
	Journal findByAuthorAndTitle(String author, String title);
}
