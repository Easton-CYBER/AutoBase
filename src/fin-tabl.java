import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.concurrent.*;

class FinTable {
    public static void main(String[] args)
    {
        FinTable fin = new FinTable();
        fin.FetchTable();
        
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

    //create instances of other classes
	MysqlCon sql = new MysqlCon();
	Table tabl = new Table();
	DefaultTableModel dtm;

	//declares variables and objects
	public Connection con;
	public String[] index;
	private JFrame jf;
	private JTable tbl;
	private JLabel label1;
	private String lnamef;
	private String vinn;
	private String licencef;
	private String date;
	private String idf;
	public String IdVal;
	
	private JMenuBar menubar;
	private JMenu menu;
    private JMenu tbls;
    private JMenuItem md;
    private JMenuItem ms;
    private JMenuItem car;
    private JMenuItem re;
	int row;
	
	private JMenuItem customer;
	String emailadd;
	String upvin;
	String uplicence;

	protected JButton fsubmit;
    protected JTextField odoread;
    protected JLabel lblodoread;
    public String odoreturn;

    //connects to mysql server
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

	//creates and draws table
	public void FetchTable()
	{
		try 
		{
			sqlCon();

			index = new String[] {"id", "Last Name", "VIN #", "Licence", "Date Finished" };
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

			car = new JMenuItem("Car Info");
			car.addActionListener(click);
			tbls.add(car);

			re = new JMenuItem("Refresh");
			re.addActionListener(click);
            menu.add(re);
            
            customer = new JMenuItem("Customer Info");
            customer.addActionListener(click);
			tbls.add(customer);

			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from `finished-repairs`;");

			tbl = new JTable();
			dtm = new DefaultTableModel(0, 0);
			dtm.setColumnIdentifiers(index);
            tbl.setModel(dtm);

			while (rs.next()) 
			{
				idf = rs.getString(1);				
				vinn = rs.getString(3);
                licencef = rs.getString(4);
				date = rs.getString(5);
				lnamef = rs.getString(2);

				dtm.insertRow(tbl.getRowCount(), new Object[] { idf, lnamef, vinn, licencef, date });
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
		}
		catch (SQLException el)
		{
			System.out.println("could not print table");
		}
	}

	//finds the value of the id of a selected row
	public String IdVal()
	{
		row = tbl.getSelectedRow();
		IdVal = tbl.getModel().getValueAt(row, 0).toString(); 

		return IdVal;
    }
    
	//waits 2 seconds then runs the refresh() method
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

	//deletes the selected row
	public void Del() 
	{
		try 
		{
			sqlCon();
			IdVal();

			PreparedStatement pstm = con.prepareStatement("DELETE FROM info_db.`finished-repairs` WHERE `fin-id` = ?;");
			pstm.setString(1, IdVal);
			pstm.executeUpdate();
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

	//refreshes table
	public void Refresh()
	{
		jf.setVisible(false);
		jf.dispose();

		FetchTable();
	}

	//opens the form to add new data to the table/mysql server
	public void Add()
	{
		Form form = new Form();
		form.Data();
	}
	
	//listens for an actions to be performed, them runs the method that corresponds to that button/action
	private class Clicklistener implements ActionListener
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
			
			if(e.getSource() == re)
			{
				Refresh();
            }
			
            if(e.getSource() == customer)
            {
                jf.dispose();
                Table tabl = new Table();
				tabl.FetchTable();
			}
            
			if(e.getSource() == car)
			{
				jf.dispose();
				CarTable ctable = new CarTable();
				ctable.FetchTable();
			}
        }
	}
}