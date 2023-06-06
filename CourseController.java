package VAC.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VAC.Dto.CoursesDto;
import VAC.Services.CourseRelatedServices;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = { "http://127.0.0.1:5173/", "http://192.168.0.102:5173/" }, allowCredentials = "true")
public class CourseController {

	@Autowired
	private CourseRelatedServices courseRelatedServices;

	@PostMapping("/create")
	public ResponseEntity<?> createCourses(@RequestBody CoursesDto coursesDto) {

		Boolean createCourseRelated = this.courseRelatedServices.createCourseRelated(coursesDto);

		if (createCourseRelated) {
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "Course created successfully...");
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);
		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("message", "Course didnot created ...");
		response.put("status", 400);

		return ResponseEntity.status(400).body(response);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCourses(@RequestBody CoursesDto coursesDto, @PathVariable int id) {

		Boolean updateCourseRelated = this.courseRelatedServices.updateCourseRelated(coursesDto, id);
		if (updateCourseRelated) {
			HashMap<String, Object> response = new HashMap<>();
			response.put("message", "Course updated successfully...");
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);
		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("message", "Course didnot updated ...");
		response.put("status", 400);

		return ResponseEntity.status(200).body(response);

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
		Boolean deleteCourseRelated = this.courseRelatedServices.deleteCourseRelated(id);
		if (deleteCourseRelated) {
			HashMap<String, Object> response = new HashMap<>();
			response.put("coursesRelated", "course deleted successfully...");
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);

		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("coursesRelated", "course didnot deleted");
		response.put("status", 400);
		return ResponseEntity.status(200).body(response);

	}
	// start

	@GetMapping("/get-all")
	public ResponseEntity<?> getAllCourses() {

		List<CoursesDto> allCourseRelatedDto = this.courseRelatedServices.getAllCourseRelated();
		HashMap<String, Object> response = new HashMap<>();
		response.put("coursesRelated", allCourseRelatedDto);
		response.put("status", 200);
		return ResponseEntity.status(200).body(response);

	}

//geting course by id with the list of year1
	@GetMapping("/get/year/{id}")
	public ResponseEntity<?> getCoursesByIdYear1(@PathVariable("id") Integer ids) {

		HashMap<String, Object> allCourseRelatedByIdDto = this.courseRelatedServices.getAllCourseRelatedByIdYear(ids);

		if (allCourseRelatedByIdDto == null) {

			HashMap<String, Object> response = new HashMap<>();
			response.put("coursesRelated", "course didnot found...");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("coursesRelated", allCourseRelatedByIdDto);
		response.put("status", 200);
		return ResponseEntity.status(200).body(response);
		//

	}

	// end

}
