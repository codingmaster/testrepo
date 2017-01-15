package com.springer.rest.api;

import com.springer.core.domain.Document;
import com.springer.core.domain.Watermark;
import com.springer.core.dto.WatermarkDto;
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
	@ResponseStatus(value = HttpStatus.CREATED)
	public WatermarkDto create(@PathVariable long documentId) throws InstantiationException, IllegalAccessException, NotFoundException
	{
		Document document = documentRepository.findOne(documentId);
		if(document == null){
			throw new NotFoundException("Document with id " + documentId + " wasn't found");
		}
		return create(document);
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public WatermarkDto create(@RequestBody Document document) throws NotFoundException
	{
		if(document == null || document.getId() == null){
			throw new NotFoundException("Document format is not recognised");
		}
		document = documentRepository.findOne(document.getId());
		Watermark watermark = new Watermark();
		watermark.setDocument(document);
		watermark = watermarkRepository.save(watermark);
		return new WatermarkDto(watermark);
	}
	
}
