import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.sql.*;

public class Mail extends Table {
    MysqlCon sql = new MysqlCon();
    Table tabl = new Table();
    Connection con;

    public void sqlCon() {
		try {
			con = DriverManager.getConnection(sql.url, sql.user, sql.password);
		} catch (SQLException el) {
			System.out.println("Could not connect to database");
		}
	}

    public void SendMail(){
        final String username = "nyazawa99@gmail.com";
        final String password = "niconiconii";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            try {
                sqlCon();

                Statement tmt = con.createStatement();
                ResultSet rs = tmt.executeQuery("select * from info WHERE id = " + IdVal + ";");
                while (rs.next()){
                    System.out.println(rs.getString(4));
                }
                System.out.println("connected to database");
            } catch (SQLException e){
                System.out.println("could not pull from database");
            };
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nyazawa99@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("farisadnanv@gmail.com"));
            message.setSubject("Testing Gmail");
            message.setText("Thankyou for choosing us for your automotive repair needs." + "\n\n We hope you are satisfied byour service. the total cost of the car repair is listed below");

            Transport.send(message);

            System.out.println("Receipt Sent");

        } catch (MessagingException e) {
            System.out.println("could not send receipt");
        }
    }

    public static void main(String[] args){
        Mail ma = new Mail();
        ma.SendMail();
    }
}