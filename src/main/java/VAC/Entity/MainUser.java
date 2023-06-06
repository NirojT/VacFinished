package VAC.Entity;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="MainUser")
public class MainUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String role;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "mainUser", fetch = FetchType.LAZY)
	private List<Notice> notices=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "mainUser", fetch = FetchType.LAZY)
	private List<Event> events=new ArrayList<>();
	
	
	
	
	
	
	

}
