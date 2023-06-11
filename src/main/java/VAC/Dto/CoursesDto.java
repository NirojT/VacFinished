package VAC.Dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoursesDto {

	private int id;

	private String title = "name";

	private String tagline;

	private String description;

	private String duration;

	private String criteria;

	private MultipartFile imageFile;

	private String faculty;

	private MultipartFile logoFile;
	

	private String imageName;
	
	
	
	private String logoName;

	// private List<Year1Dto> year1Dto;
	// private List<Year2Dto> year2Dto;
	// private List<Year3Dto> year3Dto;
	// private List<Year4Dto> year4Dto;
	// private List<Year5Dto> year5Dto;
	// private List<Year6Dto> year6Dto;
	// private List<Year7Dto> year7Dto;
	private List<SubjectsDto> subjectDto;

	private List<LevelsDto> levelsDtos;

	// public void setYear8Dto(List<Year8Dto> data) {
	// if (data.size() > 0) {
	// this.year8Dto = data;
	// }

}
