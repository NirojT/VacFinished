package VAC.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import VAC.Conversion.MainUserUserDetails;
import VAC.Entity.MainUser;
import VAC.Reposiotery.MainUserRepo;


@Component
public class MainUserUserDetailService implements UserDetailsService {

	@Autowired
	private MainUserRepo mainUserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		
		Optional<MainUser> mainUser=mainUserRepo.findByName(username);
		
		return mainUser.map(MainUserUserDetails::new)
		.orElseThrow(()-> new UsernameNotFoundException("user name not found "+username));
		
	}

}
