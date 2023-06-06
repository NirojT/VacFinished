package VAC.Reposiotery;

import org.springframework.data.jpa.repository.JpaRepository;

import VAC.Entity.Notice;

public interface NoticeRepo extends JpaRepository<Notice, Integer> {

}
