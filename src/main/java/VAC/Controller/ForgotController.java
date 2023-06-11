package VAC.Controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VAC.Services.EmailService;

@RestController
@RequestMapping("/api/forgot/")
public class ForgotController {
	
	@Autowired
	private EmailService emailService;
	//send otp 
		@PostMapping("/forgot")
		public ResponseEntity<?> sendOtp(@RequestParam String email) {
			System.out.println(email);
			
			// generating otp
			Random random=new Random(1000);
			int otp = random.nextInt(99999);
			System.out.println("otp :"+ otp);
			
			String subject="otp from vac";
			String message="otp" +otp ;
			String to=email;
			
			boolean sendEmail = this.emailService.sendEmail(subject, message, to);
			
			if (sendEmail) {
				return ResponseEntity.status(200).body("verify otp"); 
			}
			else {
				
				return ResponseEntity.status(200).body("forgot password");
			}
			
			
			
			
			
			
		}
}
