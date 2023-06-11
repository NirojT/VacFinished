package VAC.ServicesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VAC.Dto.CoursesDto;
import VAC.Dto.LevelsDto;
import VAC.Entity.Courses;
import VAC.Entity.Levels;
import VAC.Entity.Subjects;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.CourseRelatedRepo;
import VAC.Reposiotery.LevelRepo;
import VAC.Services.LevelService;

@Service
public  class LevelServiceImpl implements LevelService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private LevelRepo levelRepo;
	@Autowired
	private CourseRelatedRepo courseRelatedRepo;
	
	@Override
	public Boolean createLevel(LevelsDto levelsDto, Integer courseId) {
		try {
			Courses courses = this.courseRelatedRepo.findById(courseId)
					.orElseThrow(() -> new ResourceNotFound("course", "course Id", courseId));

			Levels level = this.mapper.map(levelsDto, Levels.class);
			level.setCourses(courses);

			this.levelRepo.save(level);
			return true;
		} catch (Exception e) {
			return false;

		}
	}
	@Override
	public List<LevelsDto> getAllLevels() {
		try {
			List<Levels> allLevels = this.levelRepo.findAll();
			List<LevelsDto> allLevelsDto = allLevels.stream().map(levels -> this.mapper.map(levels, LevelsDto.class))
					.collect(Collectors.toList());

			if (allLevels.size() > 0) {
				return allLevelsDto;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@Override
	public HashMap<String, Object> getAllLevelsId(int id) {
		try {
			Levels levelByid = this.levelRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFound("Levels", "Levels Id", id));
			HashMap<String, Object> data = new HashMap<>();

			 Courses courses = levelByid.getCourses();
			 CoursesDto map = this.mapper.map(courses, CoursesDto.class);
			 

			LevelsDto levelsDto = this.mapper.map(levelByid, LevelsDto.class);
			levelsDto.setCourses(map);
			if (levelByid != null) {
				data.put("Level", levelsDto);
				return data;

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	@Override
	public Boolean deleteLevel(int id) {
		Levels level = this.levelRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("level", "level id", id));
		this.levelRepo.delete(level);
		Optional<Levels> levels = this.levelRepo.findById(id);
		if (levels.isPresent()) {
			return false;

		}
		return true;
	}
	}


	
	