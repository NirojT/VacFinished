package VAC.ServicesImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import VAC.Dto.CoursesDto;
import VAC.Dto.LevelsDto;
import VAC.Dto.SubjectsDto;
import VAC.Entity.Courses;
import VAC.Entity.Notice;
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

		try {
			// image
			UID iUid = new UID();

			String uidString = iUid.toString().replace(':', '_');

			String originalImageFilename = coursesDto.getImageFile().getOriginalFilename();

			String extension = "";
			int dotIndex = originalImageFilename.lastIndexOf('.');
			if (dotIndex >= 0) {
				extension = originalImageFilename.substring(dotIndex);
				originalImageFilename = originalImageFilename.substring(0, dotIndex);
			}

			String imageName = originalImageFilename + "_" + uidString + extension;

			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
			Path uploadPath = Paths.get(uploadDirectory);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path imagePath = uploadPath.resolve(imageName);
			try (InputStream inputStream = coursesDto.getImageFile().getInputStream()) {
				Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new RuntimeException("Failed to store file " + originalImageFilename, e);
			}

			// logo
			UID iUidL = new UID();
			String uidStringL = iUidL.toString().replace(':', '_');
			String originalLogoFilename = coursesDto.getLogoFile().getOriginalFilename();
			String extensionL = "";
			int dotIndexL = originalLogoFilename.lastIndexOf('.');
			if (dotIndexL >= 0) {
				extensionL = originalLogoFilename.substring(dotIndexL);
				originalLogoFilename = originalLogoFilename.substring(0, dotIndexL);
			}

			String logoName = originalLogoFilename + "_" + uidStringL + extensionL;

			String uploadDirectoryL = System.getProperty("user.dir") + "/src/main/resources/static";
			Path uploadPathL = Paths.get(uploadDirectoryL);
			if (!Files.exists(uploadPathL)) {
				Files.createDirectories(uploadPathL);
			}

			Path imagePathL = uploadPathL.resolve(logoName);
			try (InputStream inputStream = coursesDto.getLogoFile().getInputStream()) {
				Files.copy(inputStream, imagePathL, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new RuntimeException("Failed to store file " + originalImageFilename, e);
			}

			Courses courses = new Courses();
			courses.setImageName("http://localhost:9191/" + imageName);
			courses.setLogoName("http://localhost:9191/" + logoName);
			courses.setTitle(coursesDto.getTitle());
			courses.setTagline(coursesDto.getTagline());
			courses.setDescription(coursesDto.getDescription());
			courses.setCriteria(coursesDto.getCriteria());
			courses.setDuration(coursesDto.getDuration());
			courses.setFaculty(coursesDto.getFaculty());

			Courses saveingCourse = courseRelatedRepo.save(courses);

			if (saveingCourse instanceof Courses) {
				return true;

			}

			return false;
		} catch (Exception e) {
			return false;
		}
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

//	@Override
//	public Boolean updateCourse
//	      ( String title,
//			String description ,
//			String tagline ,
//			String criteria ,
//			String duration,
//			String faculty,
//			String imageName ,
//			String logoName ,
//			MultipartFile logoFile,
//			MultipartFile imageFile,
//			Integer id
//			) {
//		
//
//	  	if (imageFile != null && logoFile==null ) {
//
//			UID iUids = new UID();
//
//			String uidString = iUids.toString().replace(':', '_'); // replace ':' with '_'
//			String originalFilename = imageFile.getOriginalFilename();
//
//			// Ensure that the original file name has an extension
//			String extension = "";
//			int dotIndex = originalFilename.lastIndexOf('.');
//			if (dotIndex >= 0) {
//				extension = originalFilename.substring(dotIndex);
//				originalFilename = originalFilename.substring(0, dotIndex);
//			}
//			// for multipart
//			String imageNames = originalFilename + "_" + uidString + extension;
//
//			// Save the file to the server file system
//			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
//			Path uploadPath = Paths.get(uploadDirectory);
//
//			if (!Files.exists(uploadPath)) {
//				try {
//					Files.createDirectories(uploadPath);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//			Path imagePath = uploadPath.resolve(imageNames);
//			try (InputStream inputStream = imageFile.getInputStream()) {
//				Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
//			} catch (IOException e) {
//				throw new RuntimeException("Failed to store file " + originalFilename, e);
//			} // Save the Notice in the database
//
//			Courses courses = this.courseRelatedRepo.findById(id)
//					.orElseThrow(() -> new ResourceNotFound("course", "course id", id));
//
//			String deletePhoto = courses.getImageName().replace("http://localhost:9191", "");
//			System.out.println(deletePhoto);
//			Path filePath = Paths.get(uploadDirectory, deletePhoto);
//			try {
//				Files.deleteIfExists(filePath);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			courses.setTitle(title);
//			courses.setTagline(tagline);
//			courses.setDescription(description);
//			courses.setCriteria(criteria);
//			
//			courses.setFaculty(faculty);
//			
//			courses.setDuration(duration);
//		
//			courses.setImageName("http://localhost:9191/" + imageNames);
//			courses.setLogoName(logoName);
//			this.courseRelatedRepo.save(courses);
//			return true;
//	  	}
//	  	
//	  	if (logoFile != null && imageFile==null ) {
//	  		
//	  		UID iUid = new UID();
//	  		
//	  		String uidString = iUid.toString().replace(':', '_'); // replace ':' with '_'
//	  		String originalFilename = logoFile.getOriginalFilename();
//	  		
//	  		// Ensure that the original file name has an extension
//	  		String extension = "";
//	  		int dotIndex = originalFilename.lastIndexOf('.');
//	  		if (dotIndex >= 0) {
//	  			extension = originalFilename.substring(dotIndex);
//	  			originalFilename = originalFilename.substring(0, dotIndex);
//	  		}
//	  		// for multipart
//	  		String logoNames = originalFilename + "_" + uidString + extension;
//	  		
//	  		// Save the file to the server file system
//	  		String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
//	  		Path uploadPath = Paths.get(uploadDirectory);
//	  		
//	  		if (!Files.exists(uploadPath)) {
//	  			try {
//					Files.createDirectories(uploadPath);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	  			
//	  		}
//	  		
//	  		Path imagePath = uploadPath.resolve(logoNames);
//	  		try (InputStream inputStream = logoFile.getInputStream()) {
//	  			Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
//	  		} catch (IOException e) {
//	  			throw new RuntimeException("Failed to store file " + originalFilename, e);
//	  		} // Save the Notice in the database
//	  		
//	  		Courses courses = this.courseRelatedRepo.findById(id)
//	  				.orElseThrow(() -> new ResourceNotFound("course", "course id", id));
//	  		
//	  		String deletePhoto = courses.getLogoName().replace("http://localhost:9191", "");
//	  		System.out.println(deletePhoto);
//	  		Path filePath = Paths.get(uploadDirectory, deletePhoto);
//	  		try {
//				Files.deleteIfExists(filePath);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	  		
//	  		courses.setTitle(title);
//	  		courses.setTagline(tagline);
//	  		courses.setDescription(description);
//	  		courses.setCriteria(criteria);
//	  		
//	  		courses.setFaculty(faculty);
//	  		
//	  		courses.setDuration(duration);
//	  		
//	  		courses.setImageName(imageName);
//	  		courses.setLogoName("http://localhost:9191/" +logoNames);
//	  		this.courseRelatedRepo.save(courses);
//	  		return true;
//	  	}
//	  	
//	  	
//	  	
//	  	if (logoFile != null && imageFile!=null ) {
//	  		
//	  		UID iUids = new UID();
//
//			String uidStrings = iUids.toString().replace(':', '_'); // replace ':' with '_'
//			String originalFilenames = imageFile.getOriginalFilename();
//
//			// Ensure that the original file name has an extension
//			String extensions = "";
//			int dotIndexs = originalFilenames.lastIndexOf('.');
//			if (dotIndexs >= 0) {
//				extensions = originalFilenames.substring(dotIndexs);
//				originalFilenames = originalFilenames.substring(0, dotIndexs);
//			}
//			// for multipart
//			String imageNames = originalFilenames + "_" + uidStrings + extensions;
//
//			// Save the file to the server file system
//			String uploadDirectorys = System.getProperty("user.dir") + "/src/main/resources/static";
//			Path uploadPaths = Paths.get(uploadDirectorys);
//
//			if (!Files.exists(uploadPaths)) {
//				try {
//					Files.createDirectories(uploadPaths);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//			Path imagePaths = uploadPaths.resolve(imageNames);
//			try (InputStream inputStream = imageFile.getInputStream()) {
//				Files.copy(inputStream, imagePaths, StandardCopyOption.REPLACE_EXISTING);
//			} catch (IOException e) {
//				throw new RuntimeException("Failed to store file " + originalFilenames, e);
//			} 
//			
//	  		
//	  		//for logo
//	  		
//	  		
//	  		
//	  		UID iUid = new UID();
//	  		
//	  		String uidString = iUid.toString().replace(':', '_'); // replace ':' with '_'
//	  		String originalFilename = logoFile.getOriginalFilename();
//	  		
//	  		// Ensure that the original file name has an extension
//	  		String extension = "";
//	  		int dotIndex = originalFilename.lastIndexOf('.');
//	  		if (dotIndex >= 0) {
//	  			extension = originalFilename.substring(dotIndex);
//	  			originalFilename = originalFilename.substring(0, dotIndex);
//	  		}
//	  		// for multipart
//	  		String logoNames = originalFilename + "_" + uidString + extension;
//	  		
//	  		// Save the file to the server file system
//	  		String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
//	  		Path uploadPath = Paths.get(uploadDirectory);
//	  		
//	  		if (!Files.exists(uploadPath)) {
//	  			try {
//					Files.createDirectories(uploadPath);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	  			
//	  		}
//	  		
//	  		Path imagePath = uploadPath.resolve(logoNames);
//	  		try (InputStream inputStream = logoFile.getInputStream()) {
//	  			Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
//	  		} catch (IOException e) {
//	  			throw new RuntimeException("Failed to store file " + originalFilename, e);
//	  		} 
//	  		
//	  		Courses courses = this.courseRelatedRepo.findById(id)
//	  				.orElseThrow(() -> new ResourceNotFound("course", "course id", id));
//	  	//logo deleting if exist
//	  		String deletePhoto = courses.getLogoName().replace("http://localhost:9191", "");
//	  		System.out.println(deletePhoto);
//	  		Path filePath = Paths.get(uploadDirectory, deletePhoto);
//	  		try {
//				Files.deleteIfExists(filePath);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	  		
//	  		//image deleting if exist
//	  		String deleteImage = courses.getImageName().replace("http://localhost:9191", "");
//			System.out.println(deleteImage);
//			Path filePathImage = Paths.get(uploadDirectory, deleteImage);
//			try {
//				Files.deleteIfExists(filePathImage);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	  		
//	  		courses.setTitle(title);
//	  		courses.setTagline(tagline);
//	  		courses.setDescription(description);
//	  		courses.setCriteria(criteria);
//	  		
//	  		courses.setFaculty(faculty);
//	  		
//	  		courses.setDuration(duration);
//	  		
//	  		courses.setImageName("http://localhost:9191/" +imageNames);
//	  		courses.setLogoName("http://localhost:9191/" +logoNames);
//	  		this.courseRelatedRepo.save(courses);
//	  		return true;
//	  	}
//	  	
//	 
//	  	
//	  	
//			 else {
//					try {
//						Courses courses = this.courseRelatedRepo.findById(id)
//				  				.orElseThrow(() -> new ResourceNotFound("course", "course id", id));
//						courses.setTitle(title);
//				  		courses.setTagline(tagline);
//				  		courses.setDescription(description);
//				  		courses.setCriteria(criteria);
//				  		
//				  		courses.setFaculty(faculty);
//				  		
//				  		courses.setDuration(duration);
//				  		
//				  		courses.setImageName(imageName);
//				  		courses.setLogoName(logoName);
//				  		this.courseRelatedRepo.save(courses);
//						
//							return true;
//						
//					
//					} catch (Exception e) {
//						e.printStackTrace();
//						return false;
//
//					}
//				}
//			
//	}

	@Override
	public	Boolean updateCourse ( String title,String description ,String tagline,String duration
			,String criteria ,String imageName ,String logoName,String faculty ,MultipartFile logoFile,MultipartFile imageFile,Integer id)
			throws IOException {
System.out.println(faculty);
// Check if imageFile or logoFile is not null
		if (imageFile != null || logoFile != null) {
			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
			Path uploadPath = Paths.get(uploadDirectory);

			if (!Files.exists(uploadPath)) {
				try {
					Files.createDirectories(uploadPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			Courses courses = this.courseRelatedRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFound("course", "course id", id));

			if (imageFile != null) {
				String imageNames = saveFile(uploadDirectory, uploadPath, imageFile);
				deleteExistingFile(uploadDirectory, courses.getImageName());
				courses.setImageName(imageNames);
			}

			if (logoFile != null) {
				String logoNames = saveFile(uploadDirectory, uploadPath, logoFile);
				deleteExistingFile(uploadDirectory, courses.getLogoName());
				courses.setLogoName(logoNames);
			}

// Update other fields
			courses.setTitle(title);
			courses.setTagline(tagline);
			courses.setDescription(description);
			courses.setCriteria(criteria);
			courses.setFaculty(faculty);
			courses.setDuration(duration);
			
			System.out.println("duration with file "+duration);
			System.out.println("faculty with file "+faculty);
			System.out.println("imagenaqme with file "+courses.getImageName());
			System.out.println("faculty with file "+faculty);

			this.courseRelatedRepo.save(courses);
			return true;
		} else {
			try {
				Courses courses = this.courseRelatedRepo.findById(id)
						.orElseThrow(() -> new ResourceNotFound("course", "course id", id));

// Update other fields
				courses.setTitle(title);
				courses.setTagline(tagline);
				courses.setDescription(description);
				courses.setCriteria(criteria);
				courses.setFaculty(faculty);
				courses.setDuration(duration);
				
				courses.setImageName(imageName);
				courses.setLogoName(logoName);
				System.out.println("duration "+duration);
				System.out.println("faculty "+faculty);
				this.courseRelatedRepo.save(courses);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	private String saveFile(String uploadDirectory, Path uploadPath, MultipartFile file) throws IOException {
		UID uid = new UID();
		String uidString = uid.toString().replace(':', '_');
		String originalFilename = file.getOriginalFilename();
		String extension = "";
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex >= 0) {
			extension = originalFilename.substring(dotIndex);
			originalFilename = originalFilename.substring(0, dotIndex);
		}
		String fileName = originalFilename + "_" + uidString + extension;
		Path filePath = uploadPath.resolve(fileName);
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + originalFilename, e);
		}
		return "http://localhost:9191/" + fileName;
	}

	private void deleteExistingFile(String uploadDirectory, String fileName) {
		String filePathString = fileName.replace("http://localhost:9191", "");
		Path filePath = Paths.get(uploadDirectory, filePathString);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			coursesData.put("imageName", coursesDto.getImageName());
			coursesData.put("logoName", coursesDto.getLogoName());

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

	@Override
	public HashMap<String, Object> getAllCourseOnlyByIdYear(int id) {
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
			coursesData.put("faculty", coursesDto.getFaculty());
		
			coursesData.put("criteria", coursesDto.getCriteria());
			coursesData.put("imageName", coursesDto.getImageName());
			coursesData.put("logoName", coursesDto.getLogoName());

			

	
			return coursesData;

			
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
