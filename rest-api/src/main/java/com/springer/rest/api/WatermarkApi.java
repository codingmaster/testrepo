package com.springer.rest.api;

import com.springer.core.domain.Document;
import com.springer.core.domain.Ticket;
import com.springer.core.domain.Watermark;
import com.springer.core.dto.TicketDto;
import com.springer.core.dto.WatermarkDto;
import com.springer.core.repository.DocumentRepository;
import com.springer.core.repository.WatermarkRepository;
import com.springer.core.service.WatermarkService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@RequestMapping("api/v1/watermarks")
public class WatermarkApi extends BaseApi
{
	@Autowired
	private WatermarkRepository watermarkRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private WatermarkService watermarkService;
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "{documentId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public WatermarkDto get(@PathVariable long documentId) throws InstantiationException, IllegalAccessException {
		Document document = documentRepository.findOne(documentId);
		Watermark watermark = watermarkRepository.findByDocument(document);
		if(watermark == null){
			return null;
		}
		return new WatermarkDto(watermark);
	}
	
	@Transactional
	@RequestMapping(value = "{documentId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public TicketDto create(@PathVariable long documentId) throws InstantiationException, IllegalAccessException, NotFoundException, InterruptedException
	{
		Document document = documentRepository.findOne(documentId);
		if(document == null){
			throw new NotFoundException("Document with id " + documentId + " wasn't found");
		}
		return create(document);
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public TicketDto create(@RequestBody Document document) throws NotFoundException, InterruptedException
	{
		if(document == null || document.getId() == null){
			throw new NotFoundException("Document format is not recognised");
		}
		document = documentRepository.findOne(document.getId());
		Ticket ticket = watermarkService.createTicket(document);
		final Future<Ticket> futureTicket = watermarkService.startWatermarking(ticket);
		return new TicketDto(ticket);
	}
}
