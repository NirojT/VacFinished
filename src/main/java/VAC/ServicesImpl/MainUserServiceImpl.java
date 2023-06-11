package VAC.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import VAC.Entity.MainUser;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.MainUserRepo;
import VAC.Services.MainUserService;

@Service
public class MainUserServiceImpl implements MainUserService {
	
	
	  @Autowired 
	  private PasswordEncoder encoder;
	 
	
	@Autowired
	public MainUserRepo mainUserRepo;

	@Override
	public MainUser createMainUser(MainUser mainUser) {
		//mainUser.setPassword(encoder.encode(mainUser.getPassword()));
		MainUser savedUser = this.mainUserRepo.save(mainUser);
		return savedUser;
	}

	@Override
	public MainUser updateUser(String name, String password,String email) {
		
		try {
			List<MainUser> alluser = this.mainUserRepo.findAll();
			
			System.out.println("list :"+alluser);
			
			MainUser updateMainUser = alluser.get(0);
			System.out.println("object" +updateMainUser);
		
		//MainUser updateMainUser = this.mainUserRepo.findByEmail(email).orElseThrow(()->new ResourceNotFound("user", "email", 0));
		if (name!=null || password!=null ||email!=null ) {
			if (name!=null) {
				updateMainUser.setName(name);
			}
			if (password!=null) {
				updateMainUser.setPassword(encoder.encode(password));
			}
			if (email!=null) {
				updateMainUser.setEmail(email);
			}
			MainUser updatedMainUser  = this.mainUserRepo.save(updateMainUser);
			return updatedMainUser;
		}
	
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
		
		
	}

	
	
	





}
