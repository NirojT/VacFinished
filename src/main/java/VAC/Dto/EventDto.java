package VAC.Dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    
    private String title;
    
    private String description;
    
    private MultipartFile file;
    	
    private String eventDate;
    
    private String imageName;
 
    
}



