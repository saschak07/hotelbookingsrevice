package com.hotelbooking.error;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

	private int code;
	private String description;
	private String timeStamp;
	
	
	public Error() {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar obj = Calendar.getInstance();
		this.timeStamp = df.format(obj.getTime());
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
