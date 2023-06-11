package VAC.ServicesImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.server.UID;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import VAC.Dto.EventDto;
import VAC.Entity.Event;
import VAC.Entity.Notice;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.EventRepo;
import VAC.Services.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	public EventRepo eventRepo;

	// create event
	public Boolean createEvent(EventDto eventDto) throws IOException {
		UID iUid = new UID();

		String uidString = iUid.toString().replace(':', '_'); // replace ':' with '_'
		String originalFilename = eventDto.getFile().getOriginalFilename();

		// Ensure that the original file name has an extension
		String extension = "";
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex >= 0) {
			extension = originalFilename.substring(dotIndex);
			originalFilename = originalFilename.substring(0, dotIndex);
		}

		String imageName = originalFilename + "_" + uidString + extension;

		// Save the file to the server file system
		String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";

		Path uploadPath = Paths.get(uploadDirectory);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
			System.out.println("inside try");
		}

		Path imagePath = uploadPath.resolve(imageName);
		try (InputStream inputStream = eventDto.getFile().getInputStream()) {
			Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + originalFilename, e);
		}
			// Save the Notice in the database

			Event event = new Event();
			event.setTitle(eventDto.getTitle());
			event.setDescription(eventDto.getDescription());
			event.setImageName("http://localhost:9191/" + imageName);
			event.setIsActive(false);

			event.setEventDate(eventDto.getEventDate());

			Object createdEvent = this.eventRepo.save(event);
//			try {
//				Thread.sleep(1500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			if (createdEvent instanceof Event) {
				return true;
			}

		
		return false;

	}

	// update event
	@Override
	public Boolean updateEvent(String title, String description, String imageName, String eventDate, MultipartFile file,
			Integer id) throws IOException {

		if (file != null) {
			UID iUid = new UID();

			String uidString = iUid.toString().replace(':', '_'); // replace ':' with '_'
			String originalFilename = file.getOriginalFilename();

			// Ensure that the original file name has an extension
			String extension = "";
			int dotIndex = originalFilename.lastIndexOf('.');
			if (dotIndex >= 0) {
				extension = originalFilename.substring(dotIndex);
				originalFilename = originalFilename.substring(0, dotIndex);
			}
			// for multipart
			String imageNames = originalFilename + "_" + uidString + extension;

			System.out.println(imageNames);
			System.out.println(imageName);

			// Save the file to the server file system
			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
			Path uploadPath = Paths.get(uploadDirectory);
		
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
			

			
				Path imagePath = uploadPath.resolve(imageNames);
				
				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new RuntimeException("Failed to store file " + originalFilename, e);
				}// Save the event in the database

				Event eventU = this.eventRepo.findById(id)
						.orElseThrow(() -> new ResourceNotFound("Event", "Event id", id));
				
				
				// deleting file in project folder tooo after updatimg
				
				String deletePhoto = eventU.getImageName().replace("http://localhost:9191", "");
				System.out.println(deletePhoto);
				Path filePath = Paths.get(uploadDirectory, deletePhoto);
				Files.deleteIfExists(filePath);

				eventU.setTitle(title);
				eventU.setDescription(description);
				eventU.setEventDate(eventDate);
				eventU.setImageName("http://localhost:9191/" + imageNames);
				eventU.setIsActive(false);

				Event updatedEvent = this.eventRepo.save(eventU);
//				try {
//					Thread.sleep(1500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

				if (updatedEvent instanceof Event) {

					return true;
				}


			return false;
		}
		// end of if

		else {
			try {
				// Save the Notice in the database

				Event eventU = this.eventRepo.findById(id)
						.orElseThrow(() -> new ResourceNotFound("Event", "Event id", id));

				eventU.setTitle(title);
				eventU.setDescription(description);
				eventU.setEventDate(eventDate);
				eventU.setImageName(imageName);
				eventU.setIsActive(false);

				Event updatedEvent = this.eventRepo.save(eventU);
				if (updatedEvent instanceof Event) {

					return true;
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}
		// end of else

	}

	// delete event
	@Override
	public Boolean deleteEvent(Integer eid) {

		try {
			Event deleteEvent = this.eventRepo.findById(eid)
					.orElseThrow(() -> new ResourceNotFound("Event", "Event id", eid));

			// deleting file in project folder tooo
			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";

			String deletePhoto = deleteEvent.getImageName().replace("http://localhost:9191", "");
			System.out.println(deletePhoto);
			Path filePath = Paths.get(uploadDirectory, deletePhoto);
			Files.deleteIfExists(filePath);

			this.eventRepo.delete(deleteEvent);

			Optional<Event> deleteId = this.eventRepo.findById(eid);

			if (deleteId.isPresent()) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return true;
	}

//is active
	@Override
	public Boolean updateEventIsActive(Integer eid) {

		try {

			Event updatedEvent = this.eventRepo.findById(eid)
					.orElseThrow(() -> new ResourceNotFound("Event", "Event id", eid));

			updatedEvent.setIsActive(!updatedEvent.getIsActive());

			Event isActiveEvent = this.eventRepo.save(updatedEvent);

			if (isActiveEvent instanceof Event) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return true;

	}

//get all event
	@Override
	public List<Event> getAllEvent() {
		List<Event> allEvents = this.eventRepo.findAll();
		return allEvents;
	}

	// get event by id
	@Override
	public Optional<Event> getEventById(Integer eid) {

		try {

			return Optional.ofNullable(
					this.eventRepo.findById(eid).orElseThrow(() -> new ResourceNotFound("Event", "Event id", eid)));

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

}
