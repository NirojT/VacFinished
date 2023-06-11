package VAC.Services;

import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String subject, String message,String to) {
		
		boolean f=false;
		String from="dumbd764@gmail.com";
		String password="ps4consol5";
		
		//varriable for gmail
		String host="smtp.gmail.com";
		
		
		//get the system properties
		Properties properties=System.getProperties();
		System.out.println("properties "+properties);
		
		//setting important iinformation to properties object
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		//step 1 : to get session obj
		Session session	=Session.getInstance(properties, new Authenticator() {
		
		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
			return new javax.mail.PasswordAuthentication(from,password);
		}
		});
		session.setDebug(true);
		
		
		//step 2 : compose the message (text,multi mediaa)
		MimeMessage m=new MimeMessage(session);
		try {
			//from email
			m.setFrom(from);
			
			//adding recipient to message
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//adding subject to message
			m.setSubject(subject);
			
			//adding text to message
			m.setText(message);
			
			//send
			
			//step 3: send message using transport class
			Transport.send(m);
			
			System.out.println("success..........");
			f=true;
			
		}
		catch (Exception e) {
			
			e.printStackTrace();// TODO: handle exception
		}
		return f;
		
	}

}
