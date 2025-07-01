package com.project.attable.afteraspect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

	public String convertDateForSendEmail(LocalDateTime localDate) {
		//DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy,").withLocale(Locale.US);
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("MMM dd, yyyy").withLocale(Locale.US);
		String date = formatterDate.format(localDate);
		return date;
	}
	public LocalDateTime spectifyDate(LocalDateTime date,int day) {
		LocalDateTime result = date.plusDays(day);
		return result;
	}
	
}
