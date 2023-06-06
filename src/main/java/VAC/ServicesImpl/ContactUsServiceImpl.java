package VAC.ServicesImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VAC.Entity.ContactUS;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.ContactUSRepo;
import VAC.Services.ContactUsService;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	private ContactUSRepo contactUSRepo;

	@Override
	public Boolean createContact(ContactUS contactUS) {
		/*
		 * Date date = new Date(); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy/MM/dd"); String formattedDate = sdf.format(date);
		 * System.out.println(date); System.out.println(formattedDate);
		 * 
		 * contactUS.setDate(formattedDate);
		 */
		this.contactUSRepo.save(contactUS);
		return true;
	}

	@Override
	public List<ContactUS> GetAllContacts() {
		List<ContactUS> allContacts = this.contactUSRepo.findAll();
		return allContacts;
	}

	@Override
	public Boolean deleteContact(int id) {
		ContactUS contactById = this.contactUSRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Contact", "Contact id", id));
		this.contactUSRepo.delete(contactById);
		Optional<ContactUS> contactByIdChecking = this.contactUSRepo.findById(id);
		if (contactByIdChecking.isPresent()) {
			return false;

		}

		return true;
	}

}
