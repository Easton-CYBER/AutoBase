import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

class Table {

	MysqlCon sql = new MysqlCon();

	DefaultTableModel dtm;

	public Connection con;
	public JTable tbl;

	private String[] index;
	private JFrame jf;
	private JFrame reptfr;

	private JLabel label1;
	private String fname;
	private String email;
	private String phone;
	private String home;
	
	public String lname;
	public String clname;
	public String IdVal;
	public String getEmail;

	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem md;
	private JMenuItem ms;
	private JMenuItem save;
	private JMenuItem re;
	private JMenuItem send;
	private String emailadd;
	private JMenuItem finish;

	private JMenuItem car;
	private JMenu tbls; 

	private String upname;
	private String uplname;
	private String upemail;
	private String upphone;
	private String uphome;
	private String lnamedel;
	private String search_string;

	int id;
	int row;
	int inparts;
	int inlabour;
	int inshop;
	int ingarbo;
	int partsint;
	int labourint;
	int shopint;
	int disposalint;
	int total;
	int numunit;

	protected JPanel panel;
	protected JPanel search_pan;
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
    
    protected JTextField search;

	public Table() 
	{
		try 
		{
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");

        } 
		catch (ClassNotFoundException ex)
		{
        	System.out.println("error");
        }
		catch (InstantiationException ex)
		{
        	System.out.println("error");
        }
		catch (IllegalAccessException ex)
		{
        	System.out.println("error");
        } 
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
            System.out.println("error");
        }
	}

	public void sqlCon() 
	{
		try
		{
			con = DriverManager.getConnection(sql.url, sql.user, sql.password);
		}
		catch (SQLException el)
		{
			System.out.println("Could not connect to database");
		}
	}

	public void FetchTable() 
	{
		try 
		{
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

			finish = new JMenuItem("Finished");
			finish.addActionListener(click);
			tbls.add(finish);

			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from info;");

			tbl = new JTable();
			dtm = new DefaultTableModel(0, 0);
			dtm.setColumnIdentifiers(index);
			tbl.setModel(dtm);

			TableColumnModel colmod = tbl.getColumnModel();
			colmod.getColumn(0).setPreferredWidth(40);

			while (rs.next()) 
			{
				id = rs.getInt(1);
				fname = rs.getString(2);
				lname = rs.getString(3);
				email = rs.getString(4);
				phone = rs.getString(5);
				home = rs.getString(6);

				dtm.insertRow(tbl.getRowCount(), new Object[] { id, fname, lname, email, phone, home });
			}

			tbl.setBounds(30, 40, 300, 400);
			tbl.getTableHeader().setReorderingAllowed(false);
			tbl.getTableHeader().setResizingAllowed(false);
			
			JScrollPane scrollP = new JScrollPane(tbl);
			scrollP.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
			scrollP.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 20));

			DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
			tableRenderer.setHorizontalAlignment(JLabel.LEFT);
			tbl.setDefaultRenderer(Object.class, tableRenderer);

			scrollP.setBorder(BorderFactory.createEmptyBorder());
			scrollP.setPreferredSize(new Dimension(1020, 600));
			
			label1.setFont(new Font("serif", Font.PLAIN, 20));

			search_pan = new JPanel();
			search = new JTextField(15);
			search.setText("search");
			search.setBounds(400, 100, 20, 50);
			search.addActionListener(click);
			
			search_pan.add(search);

			jf.add(label1, BorderLayout.NORTH);
			jf.add(scrollP);
			jf.add(search_pan, BorderLayout.EAST);
			jf.setJMenuBar(menubar);
			jf.setResizable(true);
			//jf.setLayout(new FlowLayout());
			jf.setSize(1035, 720);
			jf.setVisible(true);
			jf.setResizable(false);

			clname = lname;

			con.close();
		} 
		catch (SQLException el) 
		{
			System.out.println("could not print table");
		}
	}
	
	private void search_table()
	{
		search_string = search.getText();
		String sqlq = "SELECT * FROM info_db.info WHERE `First Name` LIKE  '%?%';";
		
		PreparedStatement prestat;
		try {
			prestat = con.prepareStatement(sqlq);
			prestat.setString(1, search_string);
			
			ResultSet rs = prestat.executeQuery();
			
			while(rs.next())
			{
				//output array = rs.getstring(*some number*);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String IdVal()
	{
		row = tbl.getSelectedRow();
		IdVal = tbl.getModel().getValueAt(row, 0).toString(); 

		return IdVal;
	}

	public void Wait()
	{
		try 
		{
			TimeUnit.MILLISECONDS.sleep(200);
			Refresh();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
			System.out.println("could not refresh table");
		}
	}

	public void Del()
	{
		try 
		{
			sqlCon();
			IdVal();

			PreparedStatement pstm = con.prepareStatement("DELETE FROM info_db.info WHERE id = ?;");
			pstm.setString(1, IdVal);
			pstm.executeUpdate();
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * FROM info_db.info WHERE `id` = " + IdVal + ";");
			
			while(rs.next())
			{
				lnamedel = rs.getString(3);
			}
			
			PreparedStatement ptm = con.prepareStatement("DELETE FROM info_db.`car-info` WHERE `Last Name` = ?;");
			ptm.setString(1, lnamedel);
			ptm.executeUpdate();
			
			System.out.println("Deleted row " + IdVal);
			con.close();

			try
			{
				Wait();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		catch(SQLException el)
		{
			System.out.println("an error occured: could not delete row " + IdVal);
		}
	}

	public void Refresh()
	{
		jf.setVisible(false);
		jf.dispose();

		FetchTable();
	}

	public void Add()
	{
		Form form = new Form();
		form.Data();
	}

	public void Save()
	{
		try 
		{
			sqlCon();
			IdVal();

			upname = tbl.getModel().getValueAt(row, 1).toString();
			uplname = tbl.getModel().getValueAt(row, 2).toString();
			upemail = tbl.getModel().getValueAt(row, 3).toString();
			upphone = tbl.getModel().getValueAt(row, 4).toString();
			uphome = tbl.getModel().getValueAt(row, 5).toString();

			PreparedStatement pstmt = con.prepareStatement("UPDATE info_db.info SET `First Name` = ?, `Last Name` = ?, `Email Address` = ?, `Phone Number` = ?, `Home Address` = ? WHERE id = ?;");
			pstmt.setString(1, upname);
			pstmt.setString(2, uplname);
			pstmt.setString(3, upemail);
			pstmt.setString(4, upphone);
			pstmt.setString(5, uphome);
			pstmt.setString(6, IdVal);
			pstmt.executeUpdate();

			System.out.println("updated row " + IdVal);
			con.close();

			try
			{
				Wait();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(SQLException el)
		{
			System.out.println("could not save changes to row " + IdVal);
		}
	}

	public void Rept()
	{
		reptfr = new JFrame();
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

        Clicklistener click  = new Clicklistener();
        fsubmit.addActionListener(click);
        unit.addActionListener(click);

        reptfr.setSize(600, 600);
        reptfr.setLocation(500, 280);
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

        reptfr.getContentPane().add(panel);
        reptfr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reptfr.setVisible(true);
	}
	
	public String getEmail()
	{
		try
		{
            sqlCon();
			IdVal();

            Statement tmt = con.createStatement();
            ResultSet rs = tmt.executeQuery("select * from info WHERE id = " + IdVal + ";");
            while (rs.next())
            {
				emailadd = rs.getString(4);
			}
			System.out.println("connected to database");
        } 
		catch (SQLException e)
		{
        	System.out.println("could not pull from database");
		};
		getEmail = emailadd;
		return getEmail;
	}

	public void SendMail()
	{
        final String username = "nyazawa99@gmail.com";
        final String password = "niconiconii";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
		try 
		{
            String p = part.getText();
            String l = txtlb.getText();
            String s = txtshp.getText();
            String d = txtdisp.getText();
            String n = unit.getText();
            
            partsint = Integer.parseInt(p);
            labourint = Integer.parseInt(l);
            shopint = Integer.parseInt(s);
            disposalint = Integer.parseInt(d);
            numunit = Integer.parseInt(n);
        }
		catch(NumberFormatException e)
		{
            partsint = 0;
            labourint = 0;
            shopint = 0;
            disposalint = 0;
        }

		int inparts = partsint;
		int inlabour = labourint;
		int inshop = shopint;
		int ingarbo = disposalint;

		int intparts = numunit * partsint;
		int intlabour = numunit * labourint;
		int intshop = numunit * shopint;
		int intgarbo = numunit * disposalint;
		int total = intparts + intlabour + intshop + intgarbo;
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator()
        		{
                    protected PasswordAuthentication getPasswordAuthentication() 
                    {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try 
        {
			getEmail();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nyazawa99@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailadd));
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

        } 
        catch (MessagingException e) 
        {
            System.out.println("could not send receipt");
        }
	}

	public class Clicklistener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() ==  md)
			{
				Del();
			}
			if(e.getSource() == ms)
			{
				Add();
			}
			if(e.getSource() == save)
			{
				Save();
			}
			if(e.getSource() == re)
			{
				Refresh();
			}
			if(e.getSource() == send)
			{
				Rept();
			}
			if(e.getSource() == unit)
			{
				SendMail();
				reptfr.dispose();
			}
			if(e.getSource() == fsubmit)
			{
				SendMail();
				reptfr.dispose();
				
			}
			if(e.getSource() == car)
			{
				jf.dispose();
				CarTable crtbl = new CarTable();
				crtbl.FetchTable();
			}
			if(e.getSource() == finish)
			{
				jf.dispose();
				FinTable finir = new FinTable();
				finir.FetchTable();
			}
		}
	}
}