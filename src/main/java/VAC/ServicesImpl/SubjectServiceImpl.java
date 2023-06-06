package VAC.ServicesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VAC.Dto.CoursesDto;
import VAC.Dto.SubjectsDto;
import VAC.Entity.Courses;
import VAC.Entity.Subjects;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.CourseRelatedRepo;
import VAC.Reposiotery.SubjectRepo;
import VAC.Services.SubjectService;
import net.bytebuddy.asm.Advice.Return;
import net.bytebuddy.asm.Advice.This;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepo subjectRepo;


	@Autowired
	private CourseRelatedRepo courseRelatedRepo;

	@Autowired
	private ModelMapper mapper;

	private int id2;

	@Override
	public Boolean createSubject(SubjectsDto subjectsDto, Integer courseId) {
		try {

			Courses courses = this.courseRelatedRepo.findById(courseId)
					.orElseThrow(() -> new ResourceNotFound("course", "course Id",
							courseId));
			Subjects createSubjects = this.mapper.map(subjectsDto, Subjects.class);
			createSubjects.setCourses(courses);
			this.subjectRepo.save(createSubjects);
			return true;
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}

	}

	@Override
	public List<SubjectsDto> getAllSubjects() {
		List<Subjects> allSubjects = this.subjectRepo.findAll();
		
		
//		 allSubjects.stream().map(course -> course.put("courseName",subjectRepo.findById(course.getId())))
		
				
				
		
		List<SubjectsDto> allSubjectsDtos = allSubjects.stream()
				.map(subject -> mapper.map(subject, SubjectsDto.class))
				.collect(Collectors.toList());
		System.out.println(allSubjectsDtos);
		
		//Stream<Optional<Courses>> map = allSubjectsDtos.stream().map(course -> courseRelatedRepo.findById(course.getCoursesDto().getId()) );
		
	
		

		if (allSubjectsDtos.size() > 0) {
			return allSubjectsDtos;
		}
		return null;
	}

	@Override
	public HashMap<String, Object> getAllSubjectsById(int id) {
		HashMap<String, Object> data = new HashMap<>();
		Subjects subjects = this.subjectRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Subject", "subject id", id));

//		int id2 = subjects.getCourses().getId();
		Courses courses = subjects.getCourses();
		CoursesDto courseDto = this.mapper.map(courses, CoursesDto.class);

		SubjectsDto subjectsDto = this.mapper.map(subjects, SubjectsDto.class);
		subjectsDto.setCourses(courseDto);
		if (subjects != null) {
			data.put("subjects", subjectsDto);
			return data;

		}
		return null;

	}

	@Override
	public Boolean deleteSubjects(int id) {
		Subjects subjects = this.subjectRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Subject", "subject id", id));
		this.subjectRepo.delete(subjects);
		Optional<Subjects> subject = this.subjectRepo.findById(id);
		if (subject.isPresent()) {
			return false;

		}
		return true;
	}

}
