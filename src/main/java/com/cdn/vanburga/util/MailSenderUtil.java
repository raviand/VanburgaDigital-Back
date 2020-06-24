package com.cdn.vanburga.util;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.exception.SystemPropertiesException;
import com.cdn.vanburga.model.SystemProperty;
import com.cdn.vanburga.repository.SystemPropertyRepository;

@Service
public class MailSenderUtil {
    
    
    private JavaMailSender emailSender;
	
	@Autowired
	private SystemPropertyRepository systemPropertyRepository;
	
	public JavaMailSender getMailSender() throws SystemPropertiesException {
		Optional<List<SystemProperty>> systemProperties = systemPropertyRepository.findByPropertyKeyLike("%MailManager%");
		if(systemProperties.isPresent()) {
			List<SystemProperty> propertyList = systemProperties.get();
			SystemProperty hostNameString = 			propertyList.stream().filter(s -> "MailManager.hostNameString".equals(s.getPropertyKey())).findAny().get();
			SystemProperty portString = 				propertyList.stream().filter(s -> "MailManager.portString".equals(s.getPropertyKey())).findAny().get();
			SystemProperty smtpAuthUserName = 			propertyList.stream().filter(s -> "MailManager.smtpAuthUserName".equals(s.getPropertyKey())).findAny().get();
			SystemProperty smtpAuthPassword = 			propertyList.stream().filter(s -> "MailManager.smtpAuthPassword".equals(s.getPropertyKey())).findAny().get();
			SystemProperty usePasswordAuthentication = 	propertyList.stream().filter(s -> "MailManager.usePasswordAuthentication".equals(s.getPropertyKey())).findAny().get();
			SystemProperty transportProtocol = 			propertyList.stream().filter(s -> "MailManager.transportProtocol".equals(s.getPropertyKey())).findAny().get();
			//SystemProperty smtpAuthRequired = 			propertyList.stream().filter(s -> "MailManager.smtpAuthRequired".equals(s.getPropertyKey())).findAny().get();
			
			final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();			
			mailSender.setHost(hostNameString.getNewValue());
			mailSender.setPort(portString.getNewValue() != null ? new Integer(portString.getNewValue()) : 587);
			mailSender.setProtocol(transportProtocol.getNewValue());
			mailSender.setUsername(smtpAuthUserName.getNewValue());
			mailSender.setPassword(smtpAuthPassword.getNewValue());
			Properties props = mailSender.getJavaMailProperties();
			props.put("mail.smtp.auth", usePasswordAuthentication.getNewValue());
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.debug", "true");
			
			emailSender = mailSender;
			
			return emailSender;
		}else {
			throw new SystemPropertiesException("Error getting systems properties from database");
		}
		
	}
	
	public void sendEmail(String to, String subject, String message) {

		SimpleMailMessage mail = new SimpleMailMessage(); 
			
		mail.setTo(to); 
		mail.setSubject(subject); 
		mail.setText(message);
		emailSender.send(mail);
	}
	
		
	public static boolean isValidEmail(String email) {
		 String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      return email.matches(regex);
   }
}
