import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.concurrent.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class Table {
	MysqlCon sql = new MysqlCon();
	DefaultTableModel dtm;

	public Connection con;
	String[] index;
	JFrame jf;
	JTable tbl;
	JLabel label1;
	String fname;
	public String lname;
	public String clname;
	String email;
	String phone;
	String home;
	public String IdVal;
	int id;
	int row;
	JMenuBar menubar;
	JMenu menu;
	private JMenu tbls; 
	JMenuItem md;
	JMenuItem ms;
	JMenuItem save;
	JMenuItem re;
	JMenuItem send;
	private JMenuItem car;
	String emailadd;

	String upname;
	String uplname;
	String upemail;
	String upphone;
	String uphome;

	public void sqlCon() {
		try {
			con = DriverManager.getConnection(sql.url, sql.user, sql.password);
		} catch (SQLException el) {
			System.out.println("Could not connect to database");
		}
	}

	public void FetchTable() {
		try {
			sqlCon();

			index = new String[] { "ID", "First Name", "Last Name", "Email Address", "Phone Number", "Home Address" };
			label1 = new JLabel("Customer Information");
			jf = new JFrame("Autobase: Information");

			Clicklistener click = new Clicklistener();

			menubar = new JMenuBar();
			menu = new JMenu("Edit");
			tbls = new JMenu("Tables");
			menubar.add(menu);
			menubar.add(tbls);

			md = new JMenuItem("Delete");
			md.addActionListener(click);
			menu.add(md);

			ms = new JMenuItem("Add Entry");
			ms.addActionListener(click);
			menu.add(ms);

			save = new JMenuItem("Save Changes");
			save.addActionListener(click);
			menu.add(save);

			re = new JMenuItem("Refresh");
			re.addActionListener(click);
			menu.add(re);

			send = new JMenuItem("Send Receipt");
			send.addActionListener(click);
			menu.add(send);

			car = new JMenuItem("Car Info");
			car.addActionListener(click);
			tbls.add(car);

			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from info;");

			tbl = new JTable();
			dtm = new DefaultTableModel(0, 0);
			dtm.setColumnIdentifiers(index);
			tbl.setModel(dtm);

			TableColumnModel colmod = tbl.getColumnModel();
			colmod.getColumn(0).setPreferredWidth(40);

			while (rs.next()) {
				id = rs.getInt(1);
				fname = rs.getString(2);
				lname = rs.getString(3);
				email = rs.getString(4);
				phone = rs.getString(5);
				home = rs.getString(6);

				dtm.insertRow(tbl.getRowCount(), new Object[] { id, fname, lname, email, phone, home });
			}

			tbl.setBounds(30, 40, 300, 400);

			JScrollPane scrollP = new JScrollPane(tbl);
			scrollP.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
			scrollP.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 20));

			DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
			tableRenderer.setHorizontalAlignment(JLabel.LEFT);
			tbl.setDefaultRenderer(Object.class, tableRenderer);

			scrollP.setBorder(BorderFactory.createEmptyBorder());
			scrollP.setPreferredSize(new Dimension(1020, 600));

			jf.add(label1);
			jf.add(scrollP);
			jf.setJMenuBar(menubar);
			jf.setResizable(true);
			jf.setLayout(new FlowLayout());
			jf.setSize(1035, 720);
			jf.setVisible(true);

			clname = lname;

			con.close();
		} catch (SQLException el) {
			System.out.println("could not print table");
		}
	}

	public String IdVal(){
		row = tbl.getSelectedRow();
		IdVal = tbl.getModel().getValueAt(row, 0).toString(); 

		return IdVal;
	}

	public void Receipt(){
		SendMail();
	}

	public void Wait() {
		try {
			TimeUnit.MILLISECONDS.sleep(200);
			Refresh();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("could not refresh table");
		}
	}

	public void Del() {
		try {
			sqlCon();
			IdVal();

			PreparedStatement pstm = con.prepareStatement("DELETE FROM customer_info.info WHERE id = ?;");
			pstm.setString(1, IdVal);
			pstm.executeUpdate();
			System.out.println("Deleted row " + IdVal);
			con.close();

			try {
				Wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(SQLException el){
			System.out.println("an error occured: could not delete row " + IdVal);
		}
	}

	public void Refresh(){
		jf.setVisible(false);
		jf.dispose();

		FetchTable();
	}

	public void Add(){
		Form form = new Form();
		form.Data();
	}

	public void Save(){
		try {
			sqlCon();
			IdVal();

			upname = tbl.getModel().getValueAt(row, 1).toString();
			uplname = tbl.getModel().getValueAt(row, 2).toString();
			upemail = tbl.getModel().getValueAt(row, 3).toString();
			upphone = tbl.getModel().getValueAt(row, 4).toString();
			uphome = tbl.getModel().getValueAt(row, 5).toString();

			PreparedStatement pstmt = con.prepareStatement("UPDATE customer_info.info SET `First Name` = ?, `Last Name` = ?, `Email Address` = ?, `Phone Number` = ?, `Home Address` = ? WHERE id = ?;");
			pstmt.setString(1, upname);
			pstmt.setString(2, uplname);
			pstmt.setString(3, upemail);
			pstmt.setString(4, upphone);
			pstmt.setString(5, uphome);
			pstmt.setString(6, IdVal);
			pstmt.executeUpdate();

			System.out.println("updated row " + IdVal);
			con.close();

			try{
				Wait();
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(SQLException el){
			System.out.println("could not save changes to row " + IdVal);
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
				IdVal();
				
                Statement tmt = con.createStatement();
                ResultSet rs = tmt.executeQuery("select * from info WHERE id = " + IdVal + ";");
                while (rs.next()){
					emailadd = rs.getString(4);
					System.out.println(emailadd);
                }
                System.out.println("connected to database");

            } catch (SQLException e){
                System.out.println("could not pull from database");
            };
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nyazawa99@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailadd));
            message.setSubject("Testing Gmail");
            message.setText("Thank You for Choosing us for your car repair needs. We hope you were satisfied by our service"
			+ "\n\n                                                     Receipt"
			+ "\n\n Repair 1:                                                                                    Cost:              "
			+ "\n\n Repair 2:                                                                                    Cost:              "
			+ "\n\n Repair 3:                                                                                    Cost:              "
			+ "\n\n Repair 4:                                                                                    Cost:              "
			+ "\n\n Repair 5:                                                                                    Cost:              "
			+ "\n\n Repair 6:                                                                                    Cost:              "
			+ "\n\n Repair 7:                                                                                    Cost:              "
			+ "\n\n Repair 8:                                                                                    Cost:              "
			+ "\n\n                                                                                               Total:              ");

            Transport.send(message);

            System.out.println("Receipt Sent");

        } catch (MessagingException e) {
            System.out.println("could not send receipt");
        }
    }

	private class Clicklistener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource() ==  md){
				Del();
			}
			if(e.getSource() == ms){
				Add();
			}
			if(e.getSource() == save){
				Save();
			}
			if(e.getSource() == re){
				Refresh();
			}
			if(e.getSource() == send){
				Receipt();
			}
			if(e.getSource() == car){
				jf.dispose();
				CarTable crtbl = new CarTable();
				crtbl.FetchTable();
			}
		}
	}
}
