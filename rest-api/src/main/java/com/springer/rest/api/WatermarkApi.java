package com.springer.rest.api;

import com.springer.core.domain.Document;
import com.springer.core.domain.Watermark;
import com.springer.core.repository.DocumentRepository;
import com.springer.core.repository.WatermarkRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/watermarks")
public class WatermarkApi
{
	@Autowired
	private WatermarkRepository watermarkRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "{documentId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Watermark get(@PathVariable long documentId) throws InstantiationException, IllegalAccessException {
		Document document = documentRepository.findOne(documentId);
		Watermark watermark = watermarkRepository.findByDocument(document);
		return watermark;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "{documentId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public Watermark create(@PathVariable long documentId) throws InstantiationException, IllegalAccessException, NotFoundException
	{
		Document document = documentRepository.findOne(documentId);
		if(document == null){
			throw new NotFoundException("Document with id " +documentId + " wasn't found");
		}
		Watermark watermark = new Watermark();
		watermark.setDocument(document);
		return watermark;
	}
	
	
}
