import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.mindrot.jbcrypt.BCrypt;

import java.security.*;

//creates the login frame for users to input their username and password to access the table/database information
//can also go to the new user frame by clicking the "create new user" button

public class Login extends JFrame implements ActionListener 
{
    private static final long serialVersionUID = 1L;

    //creates new instances of other classes
    MysqlCon sql = new MysqlCon();
    Connection usercon;

    //declaring variables and objects
    protected JButton blogin;
    protected JButton newuser;
    protected JPanel panel;
    protected JTextField txuser;
    protected JPasswordField pass;
    protected JLabel l1;
    protected JLabel l2;
    protected SecureRandom secrand;
    protected MessageDigest md;

    private String puname;
    private char[] ppaswd;
    private String getpass;
    private String sqlpass;

    public void Log() 
    {
    	//setting look and feel
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

        l1 = new JLabel("Username");
        l2 = new JLabel("Password");
        blogin = new JButton("login");
        panel = new JPanel();
        txuser = new JTextField();
        pass = new JPasswordField();
        newuser = new JButton("create new user");
        pass.addActionListener(this);
        blogin.addActionListener(this);
        newuser.addActionListener(this);

        setSize(300, 300);
        setLocation(500, 500);
        panel.setLayout(null);

        l1.setBounds(70, 45, 100, 30);
        l2.setBounds(70, 80, 100, 30);
        txuser.setBounds(70, 70, 150, 20);
        pass.setBounds(70, 105, 150, 20);
        blogin.setBounds(110, 140, 80, 20);
        newuser.setBounds(70, 230, 150, 20);

        panel.add(blogin);
        panel.add(txuser);
        panel.add(pass);
        panel.add(l1);
        panel.add(l2);
        panel.add(newuser);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //connects to the mysql database
    public void connectsql()
    {
        try 
        {
            usercon = DriverManager.getConnection(sql.url, sql.user, sql.password);
        } 
        catch (SQLException el)
        {
            System.out.println("could not connect to database");
        }
    }

    //checks if the password is the same as what is stored in the database
    private void check_password(String plainPassword, String hashedPassword)
    {
        if(BCrypt.checkpw(plainPassword, hashedPassword))
        {
            Table tbl = new Table();
            tbl.FetchTable();
            System.out.println("Match");
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"wrong Password / Username");
            txuser.setText("");
            pass.setText("");
            txuser.requestFocus();
        }
    }

    //listens for actions performed
    public void actionPerformed(ActionEvent e){
    	//checks for username and password and open the table if they are the same as what is stored in the database
        if(e.getSource() == pass || e.getSource() == blogin)
        {
            try
            {
                puname = txuser.getText();
                ppaswd = pass.getPassword();
                getpass = String.valueOf(ppaswd);
                connectsql();

                Statement userstat = usercon.createStatement();
                ResultSet userres = userstat.executeQuery("SELECT * FROM info_db.users WHERE username = '"+ puname +"';");

                while(userres.next())
                {
                    sqlpass = userres.getString(3);
                }

                check_password(getpass, sqlpass);
            }
            catch(SQLException el)
            {
                JOptionPane.showMessageDialog(null,"could not access user database");
                txuser.setText("");
                pass.setText("");
                txuser.requestFocus();
            }
        }
        
        //creates instance of NewUser class and runs it
        if(e.getSource() == newuser)
        {
            NewUser newuser = new NewUser();
            newuser.create_new_user();
        }
    }
}
