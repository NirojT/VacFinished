package VAC.Reposiotery;

import org.springframework.data.jpa.repository.JpaRepository;

import VAC.Entity.Courses;

public interface CourseRelatedRepo extends JpaRepository<Courses, Integer> {
	
	

}
