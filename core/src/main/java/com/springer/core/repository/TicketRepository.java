package com.springer.core.repository;

import com.springer.core.domain.Document;
import com.springer.core.domain.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends BaseRepository<Ticket>
{
	Ticket findByDocument(Document document);
}
