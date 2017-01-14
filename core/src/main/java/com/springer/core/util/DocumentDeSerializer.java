package com.springer.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springer.core.domain.Book;
import com.springer.core.domain.Journal;

import java.io.IOException;

public class DocumentDeSerializer extends JsonDeserializer
{
	private static final String TYPE = "dtype";
	private static final String BOOK = "Book";
	private static final String JOURNAL = "Journal";
	
	@Override
	public Object deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException
	{
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		ObjectNode root = mapper.readTree(jsonParser);
		if(root.has(TYPE)){
			JsonNode jsonNode = root.get(TYPE);
			if(jsonNode.asText().equalsIgnoreCase(BOOK)){
				return mapper.readValue(root.toString(), Book.class);
			}
			else if(jsonNode.asText().equalsIgnoreCase(JOURNAL)){
				return mapper.readValue(root.toString(), Journal.class);
			}
		}
		throw context.mappingException("Failed to de-serialize document, as type property not exist");
	}
}
