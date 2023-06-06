package VAC.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import VAC.Dto.EventDto;
import VAC.Entity.Event;
import VAC.Entity.Notice;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.EventRepo;
import VAC.Services.EventService;

@RestController
@RequestMapping("api/event")
@CrossOrigin(origins = {"http://127.0.0.1:5173/", "http://localhost:5173/"}, allowCredentials = "true")
public class EventController {

	@Autowired
	public EventService eventService;

	@Autowired
	public EventRepo eventRepo;

	@GetMapping("get-all")
	public ResponseEntity<?> getAllEvent() {
		List<Event> allEvent = this.eventService.getAllEvent();
		HashMap<String, Object> response = new HashMap<>();
		response.put("notices", allEvent);
		response.put("status", 200);
		return ResponseEntity.status(200).body(response);

	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getEventById(@PathVariable Integer id) {

		Optional<Event> eventById = this.eventService.getEventById(id);

		if (eventById == null) {
			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "event didnot Found");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);

		}

		HashMap<String, Object> response = new HashMap<>();

		response.put("event", eventById);
		response.put("status", 200);

		return ResponseEntity.status(200).body(response);
	}

	
	@PostMapping("/create")
	public ResponseEntity<?> createEvent(@ModelAttribute EventDto eventDto) throws IOException {
		try {
			System.out.println(eventDto.getTitle());
			System.out.println(eventDto.getEventDate());
			Boolean createEvent = this.eventService.createEvent(eventDto);
			if (createEvent) {
				HashMap<String, Object> response = new HashMap<>();
				response.put("message", "Event created successfully");
				response.put("status", 200);
		
				return ResponseEntity.status(200).body(response);
			}

			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "Event is not created ");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "Event not created");
			response.put("status", 400);
			return ResponseEntity.status(400).body(response);

			// TODO: handle exception
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEvent(@RequestParam String title, @RequestParam String description,
			@RequestParam String imageName, @RequestParam(required = false) String eventDate,
			@RequestParam(required = false) MultipartFile file, @PathVariable Integer id) {

		try {

			Boolean updateEvent = this.eventService.updateEvent(title, description, imageName, eventDate, file, id);

			if (updateEvent) {

				HashMap<String, Object> response = new HashMap<>();
				response.put("message", "Event updated successfully");
				response.put("status", 200);

				return ResponseEntity.status(200).body(response);
			}
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "Event not updated successfully");
			response.put("status", 200);

			return ResponseEntity.status(400).body(response);

		} catch (Exception e) {

			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "event create fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);

		}

	}

	// is active or not

	@PatchMapping("/active/{id}")
	public ResponseEntity<?> updateIsEventActive(@PathVariable Integer id) {

		try {

			Boolean updateEventIsActive = this.eventService.updateEventIsActive(id);
			if (updateEventIsActive == null) {
				HashMap<String, Object> response = new HashMap<>();
				response.put("message", "Event id  cannot be empty...");
				response.put("status", 200);
				return ResponseEntity.status(200).body(response);

			}

			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "Event active status is updated ");
			response.put("status", 200);

			return ResponseEntity.status(400).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "event status update fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
			// TODO: handle exception
		}
	}

	// deleting the post
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotice(@PathVariable Integer id) {
		Boolean deleteEvent = this.eventService.deleteEvent(id);
		if (!deleteEvent) {
			System.err.println();

			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "event delete fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		} else {
			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "event deleted successfully");
			response.put("status", 200);

			return ResponseEntity.status(200).body(response);
		}
	}

}
