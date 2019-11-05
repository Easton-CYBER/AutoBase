import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Receipt extends JFrame{
    private static final long serialVersionUID = 1L;

    protected JPanel panel;
    protected JButton fsubmit;
    protected JTextField part;
    protected JTextField txtlb;
    protected JTextField txtshp;
    protected JTextField txtdisp;
    protected JTextField unit;
    protected JLabel lblparts;
    protected JLabel lbllb;
    protected JLabel lblshp;
    protected JLabel lbldisp;
    protected JLabel lblunit;

    public int parts;
    public int labour;
    public int shop;
    public int disposal;
    public int numunit;

    Connection connect;

    public void Rept() {
        Font font = new Font("serif", Font.PLAIN, 18);

        panel = new JPanel();
        fsubmit = new JButton("Submit");
        fsubmit.setFont(font);

        part = new JTextField();
        lblparts = new JLabel("Cost of Parts: ");
        part.setFont(font);
        lblparts.setFont(font);

        txtlb = new JTextField();
        lbllb = new JLabel("Cost of Labour: ");
        txtlb.setFont(font);
        lbllb.setFont(font);

        txtshp = new JTextField();
        lblshp = new JLabel("Cost of Shop Supplies: ");
        txtshp.setFont(font);
        lblshp.setFont(font);

        txtdisp = new JTextField();
        lbldisp = new JLabel("Cost of Waste Disposal: ");
        txtdisp.setFont(font);
        lbldisp.setFont(font);

        unit = new JTextField();
        lblunit = new JLabel("How many units were used? ");
        unit.setFont(font);
        lblunit.setFont(font);

        Submit smbt = new Submit();
        fsubmit.addActionListener(smbt);
        unit.addActionListener(smbt);

        setSize(600, 600);
        setLocation(500, 280);
        panel.setLayout(null);

        part.setBounds(75, 40, 225, 30);
        txtlb.setBounds(300, 40, 225, 30);
        txtshp.setBounds(75, 100, 450, 30);
        txtdisp.setBounds(75, 160, 450, 30);
        unit.setBounds(75, 220, 450, 30);

        lblparts.setBounds(75, 5, 200, 40);
        lbllb.setBounds(300, 5, 200, 40);
        lblshp.setBounds(75, 65, 200, 40);
        lbldisp.setBounds(75, 125, 200, 40);
        lblunit.setBounds(75, 185, 200, 40);

        fsubmit.setBounds(200, 520, 200, 40);

        panel.add(part);
        panel.add(txtlb);
        panel.add(txtshp);
        panel.add(txtdisp);
        panel.add(unit);
        panel.add(lblparts);
        panel.add(lbllb);
        panel.add(lblshp);
        panel.add(lbldisp);
        panel.add(lblunit);
        panel.add(fsubmit);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void SendMail(){
        final String username = "nyazawa99@gmail.com";
        final String password = "niconiconii";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        try {
            String p = part.getText();
            String l = txtlb.getText();
            String s = txtshp.getText();
            String d = txtdisp.getText();
            String n = unit.getText();
            
            parts = Integer.parseInt(p);
            labour = Integer.parseInt(l);
            shop = Integer.parseInt(s);
            disposal = Integer.parseInt(d);
            numunit = Integer.parseInt(n);
        }catch(NumberFormatException e){
            parts = 0;
            labour = 0;
            shop = 0;
            disposal = 0;
        }

		int inparts = parts;
		int inlabour = labour;
		int inshop = shop;
		int ingarbo = disposal;

		int intparts = numunit * parts;
		int intlabour = numunit * labour;
		int intshop = numunit * shop;
		int intgarbo = numunit * disposal;
		int total = intparts + intlabour + intshop + intgarbo;
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nyazawa99@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("672725@pdsb.net"));
            message.setSubject("Car Repair Receipt");
			message.setText(
				"Thank you for choosing our service \n\n" +
				"Total Estimated Cost\n\n" +

				"Price Per Unit:\n" +
				"Parts: $" + inparts + "\n" +
				"Labour: $" + inlabour + "\n" +
				"Shop Supplies: $" + inshop + "\n" +
				"Disposal fee: $" + ingarbo +
				"\n\n" +
				"Line Total:\n" +
				"Parts: $" + intparts + "\n" +
				"Labour: $" + intlabour + "\n" +
				"Shop Supplies: $" + intshop + "\n" +
				"Disposal fee: $" + intgarbo + "\n\n" +
				"Total Estimated Cost: $" + total + "\n");
			
            Transport.send(message);

            System.out.println("Receipt Sent");

        } catch (MessagingException e) {
            System.out.println("could not send receipt");
        }
	}

    public class Submit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Table tab = new Table();
            dispose();
            System.out.println(tab.IdVal);
            SendMail();
        }
    }
}