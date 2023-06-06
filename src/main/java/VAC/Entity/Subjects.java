package VAC.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Subjects {

          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private int id;

          private String subjectName;

          private String level;

          @ManyToOne
          private Courses courses;

}
