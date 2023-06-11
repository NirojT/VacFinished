package VAC.Services;

import org.springframework.stereotype.Service;

import VAC.Entity.MainUser;


public interface MainUserService {
	
	
	
	MainUser createMainUser(MainUser mainUser);
	MainUser updateUser(String name,String password,String email);
	


}
