import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.event.*;
import java.sql.*;

//class used to add new users so everyone can login with their own credentials

public class NewUser
{
    public static void main(String[] args)
    {
        NewUser neu = new NewUser();
        neu.create_new_user();
        
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

    //creates new instances of required classes
    EnterData action = new EnterData();
    Table tablsql = new Table();
    Connection conn;

    //declaring objects and variables
    private JFrame new_user_frame;
    private JPanel new_user_panel;
    private JTextField new_user;
    private JPasswordField new_password;
    private JLabel new_l1;
    private JLabel new_l2;
    private JButton create;

    private String username;
    private char[] charpassword;
    private String password;

    //method to create and show the new user interface/frame
    public void create_new_user()
    {
        new_user_frame = new JFrame();
        new_user_panel = new JPanel();
        new_user = new JTextField();
        new_password = new JPasswordField();
        new_l1 = new JLabel("Enter a username");
        new_l2 = new JLabel("Enter a password");
        create = new JButton("Create user");

        new_user_frame.setSize(300, 300);
        new_user_frame.setLocation(500, 500);
        new_user_panel.setLayout(null);

        new_l1.setBounds(70, 45, 100, 30);
        new_l2.setBounds(70, 80, 100, 30);
        new_user.setBounds(70, 70, 150, 20);
        new_password.setBounds(70, 105, 150, 20);
        create.setBounds(85, 140, 120, 20);

        create.addActionListener(action);
        new_password.addActionListener(action);

        new_user_panel.add(new_user);
        new_user_panel.add(new_password);
        new_user_panel.add(new_l1);
        new_user_panel.add(new_l2);
        new_user_panel.add(create);

        new_user_frame.add(new_user_panel);
        new_user_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new_user_frame.setResizable(false);
        new_user_frame.setVisible(true);
    }

    //takes the inputed password and hashes it for storing in the database
    public String hash_pass(String plainTextPassword)
    {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

    //listens for performed actions
    class EnterData implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
        	//stores the inputed username and hashed version of password in the database
            try
            {
                username = new_user.getText();
                charpassword = new_password.getPassword();
                password = String.valueOf(charpassword);

                tablsql.sqlCon();
                conn = tablsql.con;

                PreparedStatement statpre = conn.prepareStatement("INSERT INTO `info_db`.`users` (`username`, `password`) VALUES (?, ?);");
                statpre.setString(1, username);
                statpre.setString(2, hash_pass(password));
                statpre.executeUpdate();

                new_user_frame.dispose();
            }
            catch(SQLException el)
            {
                JOptionPane.showMessageDialog(null,"Username is already taken, Please enter a new one");
                new_user.setText("");
                new_password.setText("");
                new_user.requestFocus();
            }
        }
    }
}