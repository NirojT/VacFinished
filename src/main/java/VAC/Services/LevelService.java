package VAC.Services;

import java.util.HashMap;
import java.util.List;

import VAC.Dto.LevelsDto;

public interface LevelService {

	
	 Boolean createLevel(LevelsDto levelsDto, Integer courseId);

     List<LevelsDto> getAllLevels();

     HashMap<String, Object> getAllLevelsId(int id);
     
     Boolean deleteLevel(int id);
}

