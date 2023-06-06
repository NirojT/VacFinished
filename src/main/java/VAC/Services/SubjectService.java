package VAC.Services;

import java.util.HashMap;
import java.util.List;

import VAC.Dto.SubjectsDto;

public interface SubjectService {
          Boolean createSubject(SubjectsDto subjectsDto, Integer courseId);

          List<SubjectsDto> getAllSubjects();

          HashMap<String, Object> getAllSubjectsById(int id);

          Boolean deleteSubjects(int id);
}
