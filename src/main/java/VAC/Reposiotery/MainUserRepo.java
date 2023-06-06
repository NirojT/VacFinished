package VAC.Reposiotery;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import VAC.Entity.MainUser;

public interface MainUserRepo extends JpaRepository<MainUser, Integer>
{

	Optional<MainUser> findByName(String username);

	





	
}