package com.springer.rest.api;

import com.springer.core.domain.Ticket;
import com.springer.core.dto.TicketDto;
import com.springer.core.repository.TicketRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketApi extends BaseApi {
	
	@Autowired
	private TicketRepository ticketRepository;

	@Transactional(readOnly = true)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<TicketDto> get(@PathVariable long id) throws InstantiationException, IllegalAccessException, NotFoundException
	{
		
		Ticket ticket = ticketRepository.findOne(id);
		if(ticket == null){
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		TicketDto ticketDto = new TicketDto(ticket);
		return new ResponseEntity<>(ticketDto, HttpStatus.OK);
	}
	
}
