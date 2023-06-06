package VAC.Services;

import java.util.List;
import java.util.Optional;

import VAC.Entity.ContactUS;


public interface ContactUsService {
	
	Boolean createContact(ContactUS contactUS);
	
	List<ContactUS> GetAllContacts();
	
    Boolean deleteContact(int id);

   
}
