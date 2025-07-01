package com.project.attable.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateTimeDeserializer extends StdDeserializer<LocalDateTime> {
	
	public DateTimeDeserializer() {
        super(LocalDateTime.class);
    }

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter();
		return LocalDateTime.parse(p.getValueAsString(), DATEFORMATTER); // or overloaded with an appropriate format
	}
}
