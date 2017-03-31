package com.controller;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/sendMailServlet")
public class sendMailServlet extends HttpServlet {

	private String from;
	private String passwd;
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(from, passwd);
		}
	};
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String host = "smtp.naver.com";
		String subject = "인증번호";
		from = "qkrrudtj2000@naver.com"; // 보내는 메일
		passwd = "aj0$xj0$rnf1$";
		String fromName = "Test";
		String to = " ";
		String content = " ";
		Random random = new Random();
		int num = random.nextInt(9999)+1000;

		to = request.getParameter("email");
		System.out.println(to);

		content = String.valueOf(num);
		JSONObject json = new JSONObject();
	
		try {
			// 프로퍼티 값 인스턴스 생성과 기본세션(SMTP 서버 호스트 지정)
			Properties props = new Properties();
			// 네이버 SMTP 사용시
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);

			props.put("mail.smtp.port", "465"); // 보내는 메일 포트 설정
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			// Authenticator auth = new SendMail();
			Authenticator auth = new SMTPAuthenticator();
			// Session mailSession = Session.getDefaultInstance(props,auth);
			Session mailSession = Session.getInstance(props, auth);
			mailSession.setDebug(true);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName, "UTF-8", "B"))); // 보내는
																									// 사람
																									// 설정
			InternetAddress[] address = { new InternetAddress(to) };

			msg.setRecipients(Message.RecipientType.TO, address); // 받는 사람설정

			msg.setSubject(subject); // 제목설정
			msg.setSentDate(new java.util.Date()); // 보내는 날짜 설정
			msg.setContent(content, "text/html; charset=UTF-8"); // 내용 설정(MIME
																	// 지정-HTML
																	// 형식)

			Transport.send(msg); // 메일 보내기

			System.out.println("메일 발송을 완료하였습니다.");
		} catch (MessagingException ex) {
			System.out.println("mail send error : " + ex.getMessage());
			ex.printStackTrace();
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		}
		
		json.put("content", content);
		
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
