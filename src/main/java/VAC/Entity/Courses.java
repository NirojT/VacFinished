package VAC.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Course")
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title", columnDefinition = "VARCHAR(1000)")
	private String title = "name";

	@Column( columnDefinition = "VARCHAR(1000)")
	private String tagline;

	@Column( columnDefinition = "VARCHAR(1000)")
	private String description;

	@Column( columnDefinition = "VARCHAR(1000)")
	private String duration;

	@Column(columnDefinition = "VARCHAR(1000)")
	private String criteria;
	
	private String imageName;
	
	private String faculty;
	
	private String logoName;

	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "courses", fetch = FetchType.LAZY)
	private List<Subjects> subjects = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "courses", fetch = FetchType.LAZY)
	private List<Levels> levels = new ArrayList<>();

}
