package com.web.blog.service;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


@Service
public class MailSendService {
   private boolean lowerCheck;
   private int size;

   public String init() {
      Random ran = new Random();
      StringBuffer sb = new StringBuffer();
      int num = 0;

      do {
         num = ran.nextInt(75) + 48;
         if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
            sb.append((char) num);
         } else {
            continue;
         }

      } while (sb.length() < size);
      if (lowerCheck) {
         return sb.toString().toLowerCase();
      }
      return sb.toString();

   }
   public String getKey(boolean lowerCheck, int size) {
      this.lowerCheck = lowerCheck;
      this.size = size;
      return init();

   }

   public void mailSendWithUserKey(String email, String uid,String key) {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", 587);
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");

      Session session = Session.getDefaultInstance(props,new Authenticator() {
       
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("gyw8526@gmail.com", "duddnr1223!!");
         }
      });
      String setfrom = "gyw8526@gmail.com";

      String htmlStr = "<h2>안녕하세요 MS :p ssafy 입니다!</h2><br><br>" 
            + "<h3>" + uid + "님</h3>" + "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다 : " 
            + "<a href='http://i3a501.p.ssafy.io/account/key_alter?uid="+ uid +"&userkey="+key+"'>인증하기</a></p>"
            + "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
      try {
         MimeMessage message = new MimeMessage(session);

         message.setFrom(new InternetAddress(setfrom));
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
         message.setSubject("인증 메일입니다.");
         message.setText(htmlStr, "utf-8", "html");;

         Transport.send(message);

      } catch (Exception e) {
         System.out.println(e);

      }
   }
}