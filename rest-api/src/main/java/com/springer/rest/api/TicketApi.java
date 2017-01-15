package com.springer.rest.api;

import com.springer.core.dto.TicketDto;
import com.springer.core.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public TicketDto get(@PathVariable long id) throws InstantiationException, IllegalAccessException {
		return new TicketDto(ticketRepository.findOne(id));
	}
	
}
