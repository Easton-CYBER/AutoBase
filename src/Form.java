import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Form extends JFrame {
    private static final long serialVersionUID = 1L;

    protected JPanel panel;
    protected JTextField fname;
    protected JTextField lname;
    protected JTextField email;
    protected JTextField phone;
    protected JTextField home;
    protected JButton submit;
    protected JLabel lblfname;
    protected JLabel lbllname;
    protected JLabel lblemail;
    protected JLabel lblphone;
    protected JLabel lblhome;
    public String name;
    public String lastname;
    public String inemail;
    public String inphone;
    public String inhome;

    public void Data() {

        panel = new JPanel();
        fname = new JTextField();
        lname = new JTextField();
        email = new JTextField();
        phone = new JTextField();
        home = new JTextField();
        submit = new JButton("Submit");

        Submit sbmt = new Submit();
        home.addActionListener(sbmt);
        submit.addActionListener(sbmt);

        lblfname = new JLabel("First Name:");
        lbllname = new JLabel("Last Name:");
        lblemail = new JLabel("Email Address:");
        lblphone = new JLabel("Phone Number:");
        lblhome = new JLabel("Home Address:");

        setSize(600, 425);
        setLocation(500, 280);
        panel.setLayout(null);

        fname.setBounds(75, 40, 450, 30);
        lname.setBounds(75, 100, 450, 30);
        email.setBounds(75, 160, 450, 30);
        phone.setBounds(75, 220, 450, 30);
        home.setBounds(75, 280, 450, 30);
        submit.setBounds(200, 340, 200, 40);

        lblfname.setBounds(75, 5, 200, 40);
        lbllname.setBounds(75, 65, 200, 40);
        lblemail.setBounds(75, 125, 200, 40);
        lblphone.setBounds(75, 185, 200, 40);
        lblhome.setBounds(75, 245, 200, 40);

        lblfname.setFont(new Font("serif", Font.PLAIN, 18));
        lbllname.setFont(new Font("serif", Font.PLAIN, 18));
        lblemail.setFont(new Font("serif", Font.PLAIN, 18));
        lblphone.setFont(new Font("serif", Font.PLAIN, 18));
        lblhome.setFont(new Font("serif", Font.PLAIN, 18));

        panel.add(fname);
        panel.add(lname);
        panel.add(email);
        panel.add(phone);
        panel.add(home);
        panel.add(submit);

        panel.add(lblfname);
        panel.add(lbllname);
        panel.add(lblemail);
        panel.add(lblphone);
        panel.add(lblhome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class Submit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String url;
            String user;
            String pass;

            url = "jdbc:mysql://localhost:3306/customer_Info";
            user = "root";
            pass = "!1Swamigee";

            Connection con;

            name = fname.getText();
            lastname = lname.getText();
            inemail = email.getText();
            inphone = phone.getText();
            inhome = home.getText();

            try {
                con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO `customer_info`.`info` (`First Name`, `Last Name`, `Email Address`, `Phone Number`, `Home Address`) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, name);
                pstmt.setString(2, lastname);
                pstmt.setString(3, inemail);
                pstmt.setString(4, inphone);
                pstmt.setString(5, inhome);
                pstmt.executeUpdate();

                con.close();
                setVisible(false);
                dispose();

                try{
                    Table tbl = new Table();
                    tbl.Wait();
                    dispose();
                }catch(Exception ele){
                    ele.printStackTrace();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Form frameTable = new Form();
        frameTable.Data();
    }

}
