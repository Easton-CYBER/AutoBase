import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import java.sql.*;
import java.util.concurrent.*;

class CarTable {
    public static void main(String[] args){
        CarTable car = new CarTable();
        car.FetchTable();
    }

	MysqlCon sql = new MysqlCon();
	Table table = new Table();
	DefaultTableModel dtm;

	public Connection con;
	String[] index;
	JFrame jf;
	private JTable tabl;
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
	JMenuItem finish;
	private JMenuItem customer;
	String emailadd;
	JMenuItem fini;

	String lastName;
	String upyear;
	String upmake;
	String upmodel;
	String upvin;
	String uplicence;
	String upodore;
	String upodoin;

	protected JButton fsubmit;
    protected JTextField odoread;
    protected JLabel lblodoread;
    public String odoreturn;

	String last;
	String vinstring;
	String licencestring;

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

			finish = new JMenuItem("Finished");
			finish.addActionListener(click);
			tbls.add(finish);

			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from `car-info`;");

			tabl = new JTable();
			dtm = new DefaultTableModel(0, 0);
			dtm.setColumnIdentifiers(index);
            tabl.setModel(dtm);
            
            TableColumnModel colmod = tabl.getColumnModel();
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
				

				dtm.insertRow(tabl.getRowCount(), new Object[] { id, lname, year, make, model, vin, licence,  odoint, odoout, date });
			}

			tabl.setBounds(30, 40, 500, 400);

			JScrollPane scrollP = new JScrollPane(tabl);
			scrollP.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
			scrollP.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 20));

			DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
			tableRenderer.setHorizontalAlignment(JLabel.LEFT);
			tabl.setDefaultRenderer(Object.class, tableRenderer);

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
		row = tabl.getSelectedRow();
		IdVal = tabl.getModel().getValueAt(row, 0).toString(); 

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

	public void Save(){
		try {
			sqlCon();
			IdVal();
			row = tabl.getSelectedRow();

			lastName = tabl.getModel().getValueAt(row, 1).toString();
			upyear = tabl.getModel().getValueAt(row, 2).toString();
			upmake = tabl.getModel().getValueAt(row, 3).toString();
            upmodel = tabl.getModel().getValueAt(row, 4).toString();
			upvin = tabl.getModel().getValueAt(row, 5).toString();
			uplicence = tabl.getModel().getValueAt(row, 6).toString();
			upodore = tabl.getModel().getValueAt(row, 7).toString();
			upodoin = tabl.getModel().getValueAt(row, 8).toString();

			PreparedStatement prtmt = con.prepareStatement("UPDATE customer_info.`car-info` SET `Last Name` = ?, `Year` = ?, `Make` = ?, `Model` = ?, `VIN #` = ?, `Licence Plate` = ?, `odometer reading on intake` =  ?, `odometer reading on return` = ? WHERE `id-car-info` = ?;");
			prtmt.setString(1, lastName);
			prtmt.setString(2, upyear);
			prtmt.setString(3, upmake);
            prtmt.setString(4, upmodel);
			prtmt.setString(5, upvin);
			prtmt.setString(6, uplicence);
			prtmt.setString(7, upodore);
			prtmt.setString(8, upodoin);
			prtmt.setString(9, IdVal);
			prtmt.executeUpdate();

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

	public JPanel panel;
	public JFrame frame;
	public void Form() {
		
	
        Font font = new Font("serif", Font.PLAIN, 18);

		frame = new JFrame();
        panel = new JPanel();
        fsubmit = new JButton("Submit");

        odoread = new JTextField();

        Submit sbmt = new Submit();
		odoread.addActionListener(sbmt);
		fsubmit.addActionListener(sbmt);

        lblodoread = new JLabel("Odometer Reading:");

        frame.setSize(500, 200);
        frame.setLocation(500, 280);
        panel.setLayout(null);

        odoread.setBounds(50, 50, 400, 30);
        fsubmit.setBounds(150, 100, 200, 40);
        odoread.setFont(font);
        lblodoread.setFont(font);
        lblodoread.setBounds(50, 15, 200, 40);

        panel.add(fsubmit);
        panel.add(odoread);
        panel.add(lblodoread);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public class Submit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            MysqlCon sql = new MysqlCon();

            Connection con;

            odoreturn = odoread.getText();

            try {
				IdVal();

                String carsql = "UPDATE `customer_info`.`car-info` SET `odometer reading on return` = ? WHERE `id-car-info` = ?;";
				String selsql = "SELECT * FROM `customer_info`.`car-info` WHERE `id-car-info` = " + IdVal + ";";
				String finsql = "INSERT INTO `customer_info`.`finished-repairs` (`Last Name`, `VIN #`, `Licence Plate`) VALUES (?, ?, ?);";

                con = DriverManager.getConnection(sql.url, sql.user, sql.password);
                PreparedStatement pstmt = con.prepareStatement(carsql);
				pstmt.setString(1, odoreturn);
				pstmt.setString(2, IdVal);
				pstmt.executeUpdate();
				
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(selsql);

				while(rs.next()){
					last = rs.getString(2);
					vinstring = rs.getString(6);
					licencestring = rs.getString(7);
				}

				PreparedStatement prest = con.prepareStatement(finsql);
                prest.setString(1, last);
                prest.setString(2, vinstring);
                prest.setString(3, licencestring);
                prest.executeUpdate();

                con.close();
                try{
					wait();
					Refresh();
                    frame.dispose();
                }catch(Exception ele){
                    ele.printStackTrace();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
				Form();
			}
			if(e.getSource() == finish){
				jf.dispose();
				FinTable finir = new FinTable();
				finir.FetchTable();
			}
        }
	}
}