import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.event.*;
import java.sql.*;

public class NewUser{
    public static void main(String[] args){
        NewUser neu = new NewUser();
        neu.create_new_user();
    }

    EnterData action = new EnterData();
    Table tablsql = new Table();
    Connection conn;

    JFrame new_user_frame;
    JPanel new_user_panel;
    JTextField new_user;
    JPasswordField new_password;
    JLabel new_l1;
    JLabel new_l2;
    JButton create;

    private String username;
    private char[] charpassword;
    private String password;

    public void create_new_user(){
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

    public String hash_pass(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

    class EnterData implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
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
            }catch(SQLException el){
                JOptionPane.showMessageDialog(null,"Username is already taken, Please enter a new one");
                new_user.setText("");
                new_password.setText("");
                new_user.requestFocus();
            }
        }
    }
}