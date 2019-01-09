package com.springboot.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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
import javax.mail.util.ByteArrayDataSource;

import com.springboot.entities.User;
import com.springboot.model.OrderModel;

public class MailSender {
	
	public static boolean emailRegionalManager(List<User> regionalManager, OrderModel order) {
		final String username = "noreply@hexamindz.ph";
		final String password = "n8>LPYLF";
		boolean flag = false;
		
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		if(regionalManager != null && regionalManager.size() > 0) {
			for(int i = 0; i < regionalManager.size(); i++) {
				try {
					
					String msg = 
							"<html>" +
								"<body>" +
									"<label>Dear </label> <label style=\"font-weight: bold; font-style: italic\">" + regionalManager.get(i).getNickname() + ",</label><br><br>" +
									"<label>Please be informed that a new order has been placed for your approval.</label><br>" +
									"<label>Kindly click the link below for further order details.</label><br><br>" + 
									"<a href=\"http://warehouse.hexamindz.ph/?#/orders/viewOrders/" + order.getOrderId() + "\">http://lwarehouse.hexamindz.ph/#/orders/viewOrders/" + order.getOrderId() + "</a><br><br>" + 
									"<label>Thank you!</label><br><br>" +
									"<label style=\"color:red; font-weight: bold; font-style: italic\">"
										+ "THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL</label>" + 
								"</body>" + 
							"</html>";
									
		
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("noreply@hexamindz.ph"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(regionalManager.get(i).getEmail()));
					message.setSubject("Hexamindz Warehouse - Order #" + order.getOrderId());
					message.setContent(msg, "text/html");
		
					Transport.send(message);
		
					System.out.println("Mail Successfully Sent!");
					flag =  true;
				} catch (MessagingException e) {
					e.printStackTrace();
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	public static boolean emailAccounting(List<User> accounting, OrderModel order) {
		final String username = "noreply@hexamindz.ph";
		final String password = "n8>LPYLF";
		boolean flag = false;
		
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		if(accounting != null && accounting.size() > 0) {
			for(int i = 0; i < accounting.size(); i++) {
				try {
					
					String msg = 
							"<html>" +
								"<body>" +
									"<label>Dear </label> <label style=\"font-weight: bold; font-style: italic\">" + accounting.get(i).getNickname() + ",</label><br><br>" +
									"<label>Please be informed that an order has been approved by the Regional Manager and has been assigned to you for your approval.</label><br>" +
									"<label>Kindly click the link below for further order details.</label><br><br>" + 
									"<a href=\"http://warehouse.hexamindz.ph/?#/orders/viewOrders/" + order.getOrderId() + "\">http://warehouse.hexamindz.ph/#/orders/viewOrders/" + order.getOrderId() + "</a><br><br>" + 
									"<label>Thank you!</label><br><br>" +
									"<label style=\"color:red; font-weight: bold; font-style: italic\">"
										+ "THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL</label>" + 
								"</body>" + 
							"</html>";
									
		
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("noreply@hexamindz.ph"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(accounting.get(i).getEmail()));
					message.setSubject("Hexamindz Warehouse - Order #" + order.getOrderId());
					message.setContent(msg, "text/html");
		
					Transport.send(message);
					flag = true;
					System.out.println("Mail Successfully Sent!");
				} catch (MessagingException e) {
					e.printStackTrace();
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	public static boolean emailChecker(List<User> checker, OrderModel order) {
		final String username = "noreply@hexamindz.ph";
		final String password = "n8>LPYLF";
		boolean flag = false;
		
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		if(checker != null && checker.size() > 0) {
			for(int i = 0; i < checker.size(); i++) {
				try {
					
					String msg = 
							"<html>" +
								"<body>" +
									"<label>Dear </label> <label style=\"font-weight: bold; font-style: italic\">" + checker.get(i).getNickname() + ",</label><br><br>" +
									"<label>Please be informed that an order has been approved by the Regional Manager & the Accounting staff, and has been assigned to you for releasing.</label><br>" +
									"<label>Kindly click the link below for further order details.</label><br><br>" + 
									"<a href=\"http://warehouse.hexamindz.ph/?#/orders/viewOrders/" + order.getOrderId() + "\">http://warehouse.hexamindz.ph/#/orders/viewOrders/" + order.getOrderId() + "</a><br><br>" + 
									"<label>Thank you!</label><br><br>" +
									"<label style=\"color:red; font-weight: bold; font-style: italic\">"
										+ "THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL</label>" + 
								"</body>" + 
							"</html>";
									
				
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("noreply@hexamindz.ph"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(checker.get(i).getEmail()));
					message.setSubject("Hexamindz Warehouse - Order #" + order.getOrderId());
					message.setContent(msg, "text/html");
				
					Transport.send(message);
				
					System.out.println("Mail Successfully Sent!");
					flag = true;
				} catch (MessagingException e) {
					e.printStackTrace();
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	public static boolean emailGatePass(User checker, String base64String, OrderModel order) throws UnsupportedEncodingException {
		final String username = "noreply@hexamindz.ph";
		final String password = "n8>LPYLF";
		
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("noreply@hexamindz.ph"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(checker.getEmail()));
			message.setSubject("Gatepass - Order #" + order.getOrderId());
			byte[] pdfBytes = Base64.getDecoder().decode(base64String);
			
			MimeBodyPart attachment = new MimeBodyPart();
			DataSource src = new ByteArrayDataSource(pdfBytes, "application/pdf"); 

			Multipart mp1 = new MimeMultipart();
			attachment.setDataHandler(new DataHandler(src));
			attachment.setFileName("Gatepass - Order #"+ order.getOrderId() + ".pdf");
			mp1.addBodyPart(attachment);
			message.setContent(mp1);
			Transport.send(message);

			System.out.println("Mail Successfully Sent!");
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}
}
