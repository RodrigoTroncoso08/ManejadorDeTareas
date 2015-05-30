package backend;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;
public class MailSender  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1723225568787072298L;
	String mailTarget;
	String mailSender;
	String host;
	String info;
	String Subject;
	Session session;
	String Password;
	public  MailSender(String target){
		mailSender= "grupo11ingsoft@gmail.com";
		Password="manejador";
		mailTarget=target;
		host = "smtp.gmail.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", mailSender);
        props.put("mail.smtp.password", Password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
		
		session = Session.getDefaultInstance(props);
	 
	}
	
	public void sendMail(Task task)
	{
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					DateFormat format2=new SimpleDateFormat("EEEE"); 
					String finalDay=format2.format(task.getDeadline().getTime());
					info= "Recuerde que el plazo de la tarea "+task.getName()+" vencio el día "+finalDay+
							" "+task.getDeadline().get(Calendar.DAY_OF_MONTH)+"/"+(task.getDeadline().get(Calendar.MONTH)+1)+"/"+task.getDeadline().get(Calendar.YEAR);
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(mailSender));
					message.setRecipient(RecipientType.TO,
							new InternetAddress(mailTarget));
					message.setSubject(Subject);
					message.setText(info);
		 
					Transport transport = session.getTransport("smtp");
		            transport.connect(host, mailSender, Password);
		            transport.sendMessage(message, message.getAllRecipients());
		            transport.close();
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			
			}
		});
		t.start();
		
	}

}
