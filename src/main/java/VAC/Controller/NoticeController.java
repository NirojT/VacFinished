package VAC.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import VAC.Dto.NoticeDto;
import VAC.Entity.Notice;
import VAC.Services.NoticeService;

@RestController
@RequestMapping("/api/notice")
@CrossOrigin(origins = {"http://127.0.0.1:5173/", "http://localhost:5173/"}, allowCredentials = "true")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	
	@PostMapping("/create")
	public ResponseEntity<?> createNotice(@ModelAttribute NoticeDto noticeDTO) {
		try {

			System.out.println(noticeDTO.getNoticeDate());
			Boolean createdNotice = noticeService.createNotice(noticeDTO);

			if (createdNotice) {
				HashMap<String, Object> response = new HashMap<>();
				response.put("message", "notice created successfully");
				response.put("status", 200);
				return ResponseEntity.status(200).body(response);
			}
			// else
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "notice didnot created");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);

		} catch (IOException e) {
			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "notice create fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		}
	}

	
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAllNotice() {

		List<Notice> allNotices = this.noticeService.getAllNotice();
		HashMap<String, Object> response = new HashMap<>();
		response.put("notices", allNotices);
		response.put("status", 200);
		return ResponseEntity.status(200).body(response);
		
//		return new ResponseEntity<List<Notice>>(allNotices, HttpStatus.OK);
	}

	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getNoticeById(@PathVariable Integer id) {

		Optional<Notice> noticeById = this.noticeService.getNoticeById(id);

		if (noticeById == null) {
			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "notice didnot Found");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);

		}

		HashMap<String, Object> response = new HashMap<>();

		response.put("notice", noticeById);
		response.put("status", 200);

		return ResponseEntity.status(200).body(response);
	}

	
	// deleting the post
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotice(@PathVariable Integer id) {
		Boolean deleteNotice = this.noticeService.deleteNotice(id);
		if (!deleteNotice) {

			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "notice delete fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		} else {
			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "notice deleted successfully");
			response.put("status", 200);
			

			return ResponseEntity.status(200).body(response);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateNotice(@RequestParam String title,
			@RequestParam String description ,
			@RequestParam String imageName ,
			@RequestParam(required = false) String noticeDate ,
			 @RequestParam( required = false) MultipartFile file,
			@PathVariable Integer id) throws Exception {

		try {
			
			
		
			
			
			
			Boolean updateNotice = this.noticeService.updateNotice(  title, description,  imageName,  noticeDate,  file, id);

			if (updateNotice) {
				HashMap<String, Object> response = new HashMap<>();
				response.put("message", "notice updated successfully");
				response.put("status", 200);
				return ResponseEntity.status(200).body(response);
			}

			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "notice update fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);

		} catch (IOException e) {
			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "notice create fail");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		}
		}
		
	

	@PatchMapping("/active/{id}")
	public ResponseEntity<?> updateNoticeIsActive(@PathVariable Integer id) {

		Boolean updateNoticeIsActive = this.noticeService.updateNoticeIsActive(id);

		try {
			if (updateNoticeIsActive == null) {

				HashMap<String, Object> response = new HashMap<>();

				response.put("message", "Notice with this id not found");
				response.put("status", 400);
				return ResponseEntity.status(200).body(response);

			}

			HashMap<String, Object> response = new HashMap<>();

			response.put("message", "Notice status updated successfully");
			response.put("status", 200);

			return ResponseEntity.status(200).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}


}
