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

class CarTable {
    public static void main(String[] args){
        CarTable car = new CarTable();
        car.FetchTable();
    }

	MysqlCon sql = new MysqlCon();
	Table tabl = new Table();
	DefaultTableModel dtm;

	public Connection con;
	String[] index;
	JFrame jf;
	JTable tbl;
	JLabel label1;
	String lname;
	int year;
	String make;
	String model;
	int vin;
    String licence;
    String odoint;
    String odoout;
    String date;
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
	private JMenuItem customer;
	String emailadd;
	JMenuItem fini;

	String upyear;
	String upmake;
	String upmodel;
	String upvin;
	String uplicence;

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

			index = new String[] { "ID", "Last Name", "Year", "Make", "Model", "VIN #", "Licence Plate", "Odometer reading on intake", "Odometer reading on return", "Date" };
			label1 = new JLabel("Car Iformation");
			jf = new JFrame("Autobase: Information");

			Clicklistener click = new Clicklistener();

			menubar = new JMenuBar();
            menu = new JMenu("Menu");
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
            
            customer = new JMenuItem("Customer Info");
            customer.addActionListener(click);
			tbls.add(customer);
			
			fini = new JMenuItem("Repair Finished");
			fini.addActionListener(click);
			menu.add(fini);

			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from `car-info`;");

			tbl = new JTable();
			dtm = new DefaultTableModel(0, 0);
			dtm.setColumnIdentifiers(index);
            tbl.setModel(dtm);
            
            TableColumnModel colmod = tbl.getColumnModel();
            colmod.getColumn(0).setPreferredWidth(10);
			colmod.getColumn(2).setPreferredWidth(40);
			colmod.getColumn(6).setPreferredWidth(50);
            colmod.getColumn(7).setPreferredWidth(125);
            colmod.getColumn(8).setPreferredWidth(125);

			while (rs.next()) {
				id = rs.getInt(1);
				year = rs.getInt(3);
				make = rs.getString(4);
			    model = rs.getString(5);
				vin = rs.getInt(6);
                licence = rs.getString(7);
                odoint = rs.getString(8);
                odoout = rs.getString(9);
				date = rs.getString(10);
				lname = rs.getString(2);
				

				dtm.insertRow(tbl.getRowCount(), new Object[] { id, lname, year, make, model, vin, licence,  odoint, odoout, date });
			}

			tbl.setBounds(30, 40, 500, 400);

			JScrollPane scrollP = new JScrollPane(tbl);
			scrollP.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
			scrollP.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 20));

			DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
			tableRenderer.setHorizontalAlignment(JLabel.LEFT);
			tbl.setDefaultRenderer(Object.class, tableRenderer);

			scrollP.setBorder(BorderFactory.createEmptyBorder());
			scrollP.setPreferredSize(new Dimension(1200, 720));

			jf.add(label1);
			jf.add(scrollP);
			jf.setJMenuBar(menubar);
			jf.setResizable(true);
			jf.setLayout(new FlowLayout());
			jf.setSize(1220, 720);
			jf.setVisible(true);

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

			PreparedStatement pstm = con.prepareStatement("DELETE FROM customer_info.`car-info` WHERE `id-car-info` = ?;");
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

	public void Fini(){
		FiniForm finform = new FiniForm();
		finform.Form();
	}

	public void Save(){
		try {
			sqlCon();
			IdVal();

			upyear = tbl.getModel().getValueAt(row, 1).toString();
			upmake = tbl.getModel().getValueAt(row, 2).toString();
            upmodel = tbl.getModel().getValueAt(row, 3).toString();
			upvin = tbl.getModel().getValueAt(row, 4).toString();
			uplicence = tbl.getModel().getValueAt(row, 5).toString();

			PreparedStatement pstmt = con.prepareStatement("UPDATE customer_info.`car-info` SET Year = ?, Make = ?, Model = ?, `VIN #` = ?, `Licence Plate` = ? WHERE `id-car-info` = ?;");
			pstmt.setString(1, upyear);
			pstmt.setString(2, upmake);
            pstmt.setString(3, upmodel);
			pstmt.setString(4, upvin);
			pstmt.setString(5, uplicence);
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
            message.setText("physics is bad for ur health," + "\n\n Hello World");

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
            if(e.getSource() == customer){
                jf.dispose();
                Table tabl = new Table();
                tabl.FetchTable();
			}
			if(e.getSource() == fini){
				Fini();
			}
        }
	}
}