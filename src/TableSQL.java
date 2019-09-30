import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;
import javax.swing.table.*;

public class TableSQL {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new A();
				} catch (SQLException e) {
					e.printStackTrace();
				}
}
});

}
}


class A
{
String [] index;
JFrame jf;
JTable tbl;
JLabel label1;
String fname;
String lname;
String email;
String phone;
String home;
String url1 = "jdbc:mysql://localhost:3306/customer_Info";
String user = "root";
String password = "!1Swamigee";
Connection con;

A() throws SQLException
{
	index= new String[]{"First Name", "Last Name","Email Address","Phone Number","Home Address"};
	label1 = new JLabel("Customer Information");

	con = DriverManager.getConnection(url1, user, password);
	Statement stmnt = con.createStatement();
	ResultSet rs = stmnt.executeQuery("select * from info;");

	jf= new JFrame("Autobase: Chart");

	tbl = new JTable();
	DefaultTableModel dtm = new DefaultTableModel(0, 0);
	dtm.setColumnIdentifiers(index);
	tbl.setModel(dtm);

	while(rs.next()){
		fname = rs.getString(2);
		lname = rs.getString(3);
		email = rs.getString(4);
		phone = rs.getString(5);
		home = rs.getString(6);
		
		dtm.insertRow(tbl.getRowCount(), new Object[] {fname, lname, email, phone, home});;
	}
	
	

	tbl.setBounds(30, 40, 200, 300); 

	JScrollPane scrollP = new JScrollPane(tbl);
	scrollP.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
	scrollP.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 20));

	DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
	tableRenderer.setHorizontalAlignment(JLabel.LEFT); //Aligning the table data centrally.
	tbl.setDefaultRenderer(Object.class, tableRenderer);


	scrollP.setBorder(BorderFactory.createEmptyBorder());
	scrollP.setPreferredSize(new Dimension(920,500));		

	//Setting the label2 with message to show total number of rows and columns in JTable.


	jf.add(label1);
	jf.add(scrollP);


	jf.setLayout(new FlowLayout());
	jf.setSize(935,720);
	jf.setVisible(true);

	con.close();
	}

}