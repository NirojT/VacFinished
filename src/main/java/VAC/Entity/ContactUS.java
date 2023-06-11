package VAC.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUS {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	private String title;
	private String email;
	@Lob
    private String content;
	private String phoneNo;
	

	
	private String name;
	
	

    
    
    
    
    

}
