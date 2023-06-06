package VAC.Dto;



import VAC.Entity.Courses;
import lombok.Data;

@Data
public class SubjectsDto {

          private int id;

          private String subjectName;

          private String level;

         
          
          private CoursesDto courses;



}
