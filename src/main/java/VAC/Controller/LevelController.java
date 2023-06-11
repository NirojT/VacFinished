package VAC.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VAC.Dto.LevelsDto;
import VAC.Entity.Levels;
import VAC.Services.LevelService;

@RestController
@RequestMapping("/api/level/")
@CrossOrigin(origins = { "http://127.0.0.1:5173/", "http://192.168.0.102:5173/" }, allowCredentials = "true")
public class LevelController {

	@Autowired
	private LevelService levelService;

	@PostMapping("create/{id}")
	public ResponseEntity<?> createLevel(@RequestBody LevelsDto levelDto, @PathVariable("id") Integer courseId) {
		HashMap<String, Object> response = new HashMap<>();
		Boolean createLevel = this.levelService.createLevel(levelDto, courseId);

		if (createLevel) {
			response.put("message", "Level created successfully");
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);

		}
		response.put("message", "Level didn't created ");
		response.put("status", 400);
		return ResponseEntity.status(200).body(response);

	}

	@GetMapping("get-all")
	public ResponseEntity<?> getAllLevel() {
		HashMap<String, Object> response = new HashMap<>();
		List<LevelsDto> allLevels = this.levelService.getAllLevels();
		if (allLevels.size() > 0) {
			response.put("Levels", allLevels);
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);
		}
		response.put("message", "Levels is empty ");
		response.put("status", 400);
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<?> getLevelsById(@PathVariable Integer id) {
		HashMap<String, Object> response = new HashMap<>();
		List<HashMap<String, Object>> levels=new ArrayList<>();
		HashMap<String, Object> levId = this.levelService.getAllLevelsId(id);
		if (levId.isEmpty()) {
			response.put("messsage", "no level found");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		}
		levels.add(levId);
		response.put("subject", levels);
		
		response.put("status", 200);
		return ResponseEntity.status(200).body(response);
	}
	
	@DeleteMapping("delete/{id}")
    public ResponseEntity<?> updateSubject( @PathVariable Integer id) {
              HashMap<String, Object> response = new HashMap<>();
              Boolean deleteLev = this.levelService.deleteLevel(id);
              if (deleteLev) {
                        response.put("message", "level deleted successfully");
                        response.put("status", 200);
                        return ResponseEntity.status(200).body(response);

              }
              response.put("message", "level didn't deleted ");
              response.put("status", 400);
              return ResponseEntity.status(200).body(response);

    }
	
	

}