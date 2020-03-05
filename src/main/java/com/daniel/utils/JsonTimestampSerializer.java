package com.daniel.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonTimestampSerializer extends JsonSerializer<Timestamp> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	@Override
	public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		String formatedDate = dateFormat.format(value);
		
		gen.writeString(formatedDate);
	}

}
