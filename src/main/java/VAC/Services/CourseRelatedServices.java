package VAC.Services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import VAC.Dto.CoursesDto;


public interface CourseRelatedServices {
	
	
	Boolean createCourseRelated(CoursesDto coursesDto);

	Boolean deleteCourseRelated(int id);
	List<CoursesDto> getAllCourseRelated();
	HashMap<String, Object> getAllCourseRelatedByIdYear(int id);
	HashMap<String, Object> getAllCourseOnlyByIdYear(int id);
	
	Boolean updateCourse ( String title,String description ,String tagline,String duration
			,String criteria ,String imageName ,String logoName,String faculty ,MultipartFile logoFile,MultipartFile imageFile,Integer id)
			throws IOException;

}
