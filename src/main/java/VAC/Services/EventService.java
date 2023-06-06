package VAC.Services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import VAC.Dto.EventDto;
import VAC.Dto.NoticeDto;
import VAC.Entity.Event;
import VAC.Entity.Notice;

public interface EventService {
	
	
	
	
	//dto way of creating event
	Boolean createEvent(EventDto eventDto) throws IOException; 
	
	
	
	
	
	//update Event

	Boolean updateEvent(String title, String description, String imageName, String eventDate, MultipartFile file,Integer id) throws IOException;
	
	
	
	
	//update event is Active
	Boolean updateEventIsActive( Integer id);
	
	//get all notice
	List<Event> getAllEvent();
	
	

	
	// get Event by id
	
	 Optional<Event> getEventById(Integer id);
	
	
	//delete event
	 Boolean deleteEvent(Integer id);



}
