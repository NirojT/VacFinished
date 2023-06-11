package VAC.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VAC.Entity.ContactUS;
import VAC.Reposiotery.ContactUSRepo;
import VAC.Services.ContactUsService;


@RestController
@RequestMapping("api/contact")
@CrossOrigin(origins = {"http://127.0.0.1:5173/", "http://localhost:5173/"}, allowCredentials = "true")
public class ContactUsController {
	@Autowired
	public ContactUsService contactUsService;

	
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createContact(@RequestBody ContactUS contactUS){
		
		System.out.println(contactUS.getTitle());
		System.out.println(contactUS.getName());
		System.out.println(contactUS.getContent());
		System.out.println(contactUS.getEmail());
		System.out.println(contactUS.getPhoneNo());
		HashMap<String, Object> response=new HashMap<>();
		Boolean createdContact = this.contactUsService.createContact(contactUS);
		if(createdContact) {
			
			response.put("message", "Contact cretaed ");
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);
		}
		response.put("message", "Contact not  cretaed ");
		response.put("status", 400);
		return ResponseEntity.status(200).body(response);
		
	}
		
	
		@GetMapping("/get-all")
		public ResponseEntity<?> getAllContact(){
			HashMap<String, Object> response=new HashMap<>();
			List<ContactUS> allContacts = this.contactUsService.GetAllContacts();
			
			if(allContacts.isEmpty()) {
				response.put("message", "no contacts are fetched..");
				response.put("status", 200);
				return ResponseEntity.status(200).body(response);
			}
			response.put("contacts", allContacts);
			response.put("status", 200);
			return ResponseEntity.status(200).body(response);
			
		}
		
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> deleteContact(@PathVariable ("id")Integer ids){
			HashMap<String, Object> response=new HashMap<>();
			Boolean deleteContact = this.contactUsService.deleteContact(ids);
			if(deleteContact) {
				response.put("message", " contacts deleted..");
				response.put("status", 200);
				return ResponseEntity.status(200).body(response);
			}
			response.put("message", " contacts not deleted..");
			response.put("status", 400);
			return ResponseEntity.status(200).body(response);
		}
		
		

	
	

}
