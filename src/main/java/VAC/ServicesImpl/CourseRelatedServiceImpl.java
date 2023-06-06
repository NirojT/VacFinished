package VAC.ServicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VAC.Dto.CoursesDto;
import VAC.Dto.LevelsDto;
import VAC.Dto.SubjectsDto;
import VAC.Entity.Courses;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.CourseRelatedRepo;
import VAC.Services.CourseRelatedServices;

@Service
public class CourseRelatedServiceImpl implements CourseRelatedServices {

	@Autowired
	private CourseRelatedRepo courseRelatedRepo;

	@Autowired
	private ModelMapper mapper;

	// creating courses..........
	@Override
	public Boolean createCourseRelated(CoursesDto coursesDto) {

		Courses courses = this.mapper.map(coursesDto, Courses.class);

		// Courses courses2=new Courses();
		// courses2.setTitle(courses.getTitle());
		// courses2.setCriteria(courses.getCriteria());
		// courses2.setDescription(courses.getDescription());
		// courses2.setDuration(courses.getDuration());
		// courses2.setTagline(courses.getTagline());

		Courses saveingCourse = courseRelatedRepo.save(courses);

		if (saveingCourse instanceof Courses) {
			return true;

		}

		return false;
	}

	/*
	 * //updating course
	 * 
	 * @Override public Boolean updateCourseRelated(Courses courses,int id) {
	 * 
	 * Courses courseById = this.courseRelatedRepo.findById(id).orElseThrow(()-> new
	 * ResourceNotFound("Course", "Courses id", id));
	 * 
	 * 
	 * courseById.setTitle(courses.getTitle());
	 * courseById.setCriteria(courses.getCriteria());
	 * courseById.setDescription(courses.getDescription());
	 * courseById.setDuration(courses.getDuration());
	 * courseById.setTagline(courses.getTagline());
	 * 
	 * 
	 * Courses updatedCourse = this.courseRelatedRepo.save(courseById);
	 * 
	 * if (updatedCourse instanceof Courses) { return true;
	 * 
	 * }
	 * 
	 * return false; }
	 * 
	 * //deleting courses
	 * 
	 * @Override public Boolean deleteCourseRelated(int id) {
	 * 
	 * Courses courseById = this.courseRelatedRepo.findById(id).orElseThrow(()-> new
	 * ResourceNotFound("Course", "Courses id", id));
	 * 
	 * this.courseRelatedRepo.delete(courseById); Optional<Courses> deletedCourse =
	 * this.courseRelatedRepo.findById(id);
	 * 
	 * if(deletedCourse.isPresent()) { return false; }
	 * 
	 * 
	 * 
	 * return true; }
	 * 
	 * // getting all courses
	 * 
	 * @Override public List<Courses> getAllCourseRelated() { List<Courses>
	 * allCourses = this.courseRelatedRepo.findAll(); return allCourses; }
	 * 
	 * // getting all course by id
	 * 
	 * @Override public Optional<Courses> getAllCourseRelatedById(int id) { try {
	 * 
	 * return this.courseRelatedRepo.findById(id);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return null; }
	 */

	// }

	@Override
	public Boolean updateCourseRelated(CoursesDto coursesDto, int id) {

		Courses courseById = this.courseRelatedRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Course", "Courses id", id));
		courseById.setTitle(coursesDto.getTitle());
		courseById.setCriteria(coursesDto.getCriteria());
		courseById.setDescription(coursesDto.getDescription());
		courseById.setDuration(coursesDto.getDuration());
		courseById.setTagline(coursesDto.getTagline());

		Courses savedCourses = this.courseRelatedRepo.save(courseById);
		if (savedCourses instanceof Courses) {
			return true;
		}
		return false;

	}

	@Override
	public Boolean deleteCourseRelated(int id) {
		Courses courseById = this.courseRelatedRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Course", "Courses id", id));

		this.courseRelatedRepo.delete(courseById);
		Optional<Courses> deletedCourse = this.courseRelatedRepo.findById(id);

		if (deletedCourse.isPresent()) {
			return false;
		}

		return true;
	}

	@Override
	public List<CoursesDto> getAllCourseRelated() {
		List<Courses> allCOurses = this.courseRelatedRepo.findAll();

		List<CoursesDto> listDtos = allCOurses.stream().map(courses -> this.mapper.map(courses, CoursesDto.class))
				.collect(Collectors.toList());

		return listDtos;
	}

	@Override
	public HashMap<String, Object> getAllCourseRelatedByIdYear(int id) {
		Courses courseById = this.courseRelatedRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Course", "Courses id", id));

		if (courseById != null) {
			CoursesDto coursesDto = this.mapper.map(courseById, CoursesDto.class);
			coursesDto.setSubjectDto(courseById.getSubjects().stream()
					.map(sub -> this.mapper.map(sub, SubjectsDto.class)).collect(Collectors.toList()));

			coursesDto.setLevelsDtos(courseById.getLevels().stream().map(level -> mapper.map(level, LevelsDto.class))
					.collect(Collectors.toList()));

			HashMap<String, Object> coursesData = new HashMap<>();
			coursesData.put("id", coursesDto.getId());
			coursesData.put("title", coursesDto.getTitle());
			coursesData.put("tagline", coursesDto.getTagline());
			coursesData.put("description", coursesDto.getDescription());
			coursesData.put("duration", coursesDto.getDuration());
			coursesData.put("criteria", coursesDto.getCriteria());

			
			
			
			List<SubjectsDto> subjectDto = coursesDto.getSubjectDto();
		
			List<LevelsDto> levelsDtos = coursesDto.getLevelsDtos();
			
			
			coursesData.put("subjects", subjectDto);
			coursesData.put("levels", levelsDtos);
			return coursesData;
			
			 
			
			
			/*
			 * if (coursesDto.getLevelsDtos().size() > 0) { data.put("Level",
			 * coursesDto.getLevelsDtos()); coursesData.put("levels", data); return
			 * coursesData; }
			 */
		}
		return null;
	}
}

// geting year
/*
 * @Override public HashMap<String, Object> getAllCourseRelatedByIdYear(int id)
 * { Courses courseById = this.courseRelatedRepo.findById(id) .orElseThrow(() ->
 * new ResourceNotFound("Course", "Courses id", id));
 * 
 * if (courseById != null) { CoursesDto coursesDto = this.mapper.map(courseById,
 * CoursesDto.class);
 * coursesDto.setYear1Dto(courseById.getYear1().stream().map(year1 ->
 * this.mapper.map(year1, Year1Dto.class)) .collect(Collectors.toList()));
 * 
 * coursesDto.setYear2Dto(courseById.getYear2().stream().map(year2 ->
 * this.mapper.map(year2, Year2Dto.class)) .collect(Collectors.toList()));
 * 
 * coursesDto.setYear3Dto(courseById.getYear3().stream().map(year3 ->
 * this.mapper.map(year3, Year3Dto.class)) .collect(Collectors.toList()));
 * 
 * 
 * coursesDto.setYear4Dto(courseById.getYear4().stream().map(year4 ->
 * this.mapper.map(year4, Year4Dto.class)) .collect(Collectors.toList()));
 * 
 * coursesDto.setYear5Dto(courseById.getYear5().stream().map(year5 ->
 * this.mapper.map(year5, Year5Dto.class)) .collect(Collectors.toList()));
 * 
 * coursesDto.setYear6Dto(courseById.getYear6().stream().map(year6 ->
 * this.mapper.map(year6, Year6Dto.class)) .collect(Collectors.toList()));
 * 
 * coursesDto.setYear7Dto(courseById.getYear7().stream().map(year7 ->
 * this.mapper.map(year7, Year7Dto.class)) .collect(Collectors.toList()));
 * 
 * coursesDto.setYear8Dto(courseById.getYear8().stream().map(year8 ->
 * this.mapper.map(year8, Year8Dto.class)) .collect(Collectors.toList()));
 * 
 * HashMap<String, Object> coursesData = new HashMap<>(); coursesData.put("id",
 * coursesDto.getId()); coursesData.put("title", coursesDto.getTitle());
 * coursesData.put("tagline", coursesDto.getTagline());
 * coursesData.put("description", coursesDto.getDescription());
 * coursesData.put("duration", coursesDto.getDuration());
 * coursesData.put("criteria", coursesDto.getCriteria());
 * 
 * HashMap<String, Object> data = new HashMap<>(); if (coursesDto.getYear1Dto()
 * .size() > 0) { data.put("YearOne", coursesDto.getYear1Dto()); }
 * 
 * if (coursesDto.getYear2Dto() .size() > 0 ) { data.put("YearTwo",
 * coursesDto.getYear2Dto()); }
 * 
 * if (coursesDto.getYear3Dto() .size() > 0) { data.put("YearThree",
 * coursesDto.getYear3Dto());
 * 
 * }
 * 
 * if (coursesDto.getYear4Dto() .size() > 0) { data.put("YearFour",
 * coursesDto.getYear4Dto()); }
 * 
 * if (coursesDto.getYear5Dto() .size() > 0) { data.put("YearFive",
 * coursesDto.getYear5Dto()); }
 * 
 * if (coursesDto.getYear6Dto() .size() > 0) { data.put("YearSix",
 * coursesDto.getYear6Dto()); }
 * 
 * if (coursesDto.getYear7Dto() .size() > 0) { data.put("YearSeven",
 * coursesDto.getYear7Dto()); }
 * 
 * if (coursesDto.getYear8Dto() .size() > 0) { data.put("YearEight",
 * coursesDto.getYear8Dto()); }
 * 
 * data.put("course", coursesData);
 * 
 * return data;
 * 
 * //
 * 
 * } // return this.mapper.map(courseById, CoursesDto.class); HashMap<String,
 * Object> data = new HashMap<>(); data.put("YearOne", "hello"); return data; }
 */
