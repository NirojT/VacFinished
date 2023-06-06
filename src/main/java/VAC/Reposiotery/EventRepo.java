package VAC.Reposiotery;

import org.springframework.data.jpa.repository.JpaRepository;

import VAC.Entity.Event;

public interface EventRepo extends JpaRepository<Event, Integer> {
	
	

}
