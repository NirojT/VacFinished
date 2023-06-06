package VAC.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import VAC.Dto.CoursesDto;


public interface CourseRelatedServices {
	
	
	Boolean createCourseRelated(CoursesDto coursesDto);
	Boolean updateCourseRelated(CoursesDto coursesDto,int id);
	Boolean deleteCourseRelated(int id);
	List<CoursesDto> getAllCourseRelated();
	HashMap<String, Object> getAllCourseRelatedByIdYear(int id);
	


}
