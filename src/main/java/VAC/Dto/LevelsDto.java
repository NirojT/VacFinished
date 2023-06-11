package VAC.Dto;
import VAC.Entity.Courses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevelsDto {
	private int id;
	private String levelName;
	 private CoursesDto courses;
}
