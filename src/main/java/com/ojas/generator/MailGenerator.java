package com.ojas.generator;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailGenerator {

//	@Scheduled(cron="0 0/1 * 1/1 * ?")
//	public void send() {
//		String filename = "C:\\Users\\sp22282\\Documents\\userlist.pdf";
//		mailGen("venu1382@gmail.com", filename);
//	}

	public static boolean mailGen(String email, String filename) {
		String to = email;
		boolean flag = false;

		// change accordingly
		final String user = "shivatejapothapragada7@gmail.com";// change accordingly
		final String password = "viwqmgwlvzriujai";// change accordingly

		// 1) get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.mime.address.strict", "false");

		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(user, password);
			}
		});

		// 2) compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Here are the Details of users ");

			// 3) create MimeBodyPart object and set your message text
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Hello Mr.Aravind ");

			// 4) create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = (DataSource) new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler((javax.activation.DataSource) source));
			messageBodyPart2.setFileName(filename);

			// 5) create Multipart object and add MimeBodyPart objects to this object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			Transport.send(message);
			flag = true;

		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

		return flag;
	}
}
