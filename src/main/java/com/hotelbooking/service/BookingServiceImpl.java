package com.hotelbooking.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.hotelbooking.dao.HotelDetailDao;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.exception.NoDetailsFoundException;
@Service
public class BookingServiceImpl implements BookingService {
	@Value("${calendar.application.name}")
	private  String APPLICATION_NAME ;
    private  JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    @Value("${calendar.token.path}")
    private  String TOKENS_DIRECTORY_PATH = "tokens";
    private List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    @Value("${calendar.credntial.path}")
    private String CREDENTIALS_FILE_PATH = "/credentials.json";
    @Value("${calendar.access.type}")
    private String ACCESS_TYPE;
    @Value("${calendar.auth.as}")
    private String AUTH_AS;
    @Value("${calendar.company.email}")
    private String COMPANY_EMAIL;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HotelDetailDao hotelDetailDao;

    private  Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = this.getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType(ACCESS_TYPE)
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(AUTH_AS);
    }


	@Override
	public List<Event> getEvents() throws Exception{
		 // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
        	logger.info("No upcoming events found.");
            return new ArrayList<Event>();
        } else {
        	logger.info("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                logger.info("%s (%s)\n", event.getSummary(), start);
                ;
            }

		
        }
        return items;
	}


	@Override
	public Optional<Event> bookRoom(BookingRequestDto bookingRequest) throws Exception {
		// Build a new authorized API client service.
		 Event event = new Event();
		 try {
			 Optional<HotelDto> hotelOptional = hotelDetailDao.getHotelById(bookingRequest.getHotelId());
			 if(!hotelOptional.isPresent()) {
				 throw new NoDetailsFoundException("hotel:"+bookingRequest.getHotelId()+"Does not exist");
			 }
			 final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			 Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
       
        		event.setSummary("Room:"+bookingRequest.getRoomNo()+" booked for"+bookingRequest.getGuestName())
        	    .setLocation(hotelOptional.get().getAddress())
        	    .setDescription(bookingRequest.getHotelId()+"|"+bookingRequest.getHotelName()+"|"+bookingRequest.getRoomNo());

        		DateTime startDateTime = bookingRequest.getStartDate();
        		EventDateTime start = new EventDateTime()
        	    .setDateTime(startDateTime)
        	    .setTimeZone("America/Los_Angeles");
        		event.setStart(start);

        		DateTime endDateTime = bookingRequest.getEndDate();
        		EventDateTime end = new EventDateTime()
        	    .setDateTime(endDateTime)
        	    .setTimeZone("America/Los_Angeles");
        		event.setEnd(end);

        		EventAttendee[] attendees = new EventAttendee[] {
        	    new EventAttendee().setEmail(bookingRequest.getGuestEmail()),
        	    new EventAttendee().setEmail(COMPANY_EMAIL),
        		};
        		event.setAttendees(Arrays.asList(attendees));

        		EventReminder[] reminderOverrides = new EventReminder[] {
        	    new EventReminder().setMethod("email").setMinutes(24 * 60),
        	    new EventReminder().setMethod("popup").setMinutes(10),
        		};
        		Event.Reminders reminders = new Event.Reminders()
        	    .setUseDefault(false)
        	    .setOverrides(Arrays.asList(reminderOverrides));
        		event.setReminders(reminders);

        		String calendarId = "primary";
        		event = service.events().insert(calendarId, event).execute();
	        	logger.info("Event created"+ event.getHtmlLink());
	        	hotelDetailDao.bookRoom(bookingRequest);
			 }
		 catch(Exception e) {
			 logger.error("error while syncing room booking"
			 + " request with google calendar for booking request:"
			 +new ObjectMapper().writeValueAsString(bookingRequest), e);
			 throw e;
		 }

		return Optional.of(event);
	}
	
}
