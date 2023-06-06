package VAC.ServicesImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import VAC.Entity.MainUser;
import VAC.Reposiotery.MainUserRepo;
import VAC.Services.MainUserService;

@Service
public class MainUserServiceImpl implements MainUserService {
	
	
	  @Autowired private PasswordEncoder encoder;
	 
	
	@Autowired
	public MainUserRepo mainUserRepo;

	@Override
	public MainUser createMainUser(MainUser mainUser) {
		//mainUser.setPassword(encoder.encode(mainUser.getPassword()));
		MainUser savedUser = this.mainUserRepo.save(mainUser);
		return savedUser;
	}
	
	
	





}
