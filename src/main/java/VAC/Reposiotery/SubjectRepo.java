package VAC.Reposiotery;

import org.springframework.data.jpa.repository.JpaRepository;

import VAC.Entity.Subjects;

public interface SubjectRepo extends JpaRepository<Subjects, Integer> {

}
