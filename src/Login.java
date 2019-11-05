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

import org.mindrot.jbcrypt.BCrypt;

import java.security.*;

public class Login extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    MysqlCon sql = new MysqlCon();
    Connection usercon;

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

    public void Log() {

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

    public void connectsql() {
        try {
            usercon = DriverManager.getConnection(sql.url, sql.user, sql.password);
        } catch (SQLException el) {
            System.out.println("could not connect to database");
        }
    }

    private void check_password(String plainPassword, String hashedPassword){
        if(BCrypt.checkpw(plainPassword, hashedPassword)){
            Table tbl = new Table();
            tbl.FetchTable();
            System.out.println("Match");
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"wrong Password / Username");
            txuser.setText("");
            pass.setText("");
            txuser.requestFocus();
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == pass || e.getSource() == blogin){
            try{
                puname = txuser.getText();
                ppaswd = pass.getPassword();
                getpass = String.valueOf(ppaswd);
                connectsql();

                Statement userstat = usercon.createStatement();
                ResultSet userres = userstat.executeQuery("SELECT * FROM customer_info.users WHERE username = '"+ puname +"';");

                while(userres.next()){
                    sqlpass = userres.getString(3);
                }

                check_password(getpass, sqlpass);
            }catch(SQLException el){
                JOptionPane.showMessageDialog(null,"could not access user database");
                txuser.setText("");
                pass.setText("");
                txuser.requestFocus();
            }
        }
        if(e.getSource() == newuser){
            NewUser newuser = new NewUser();
            newuser.create_new_user();
        }
    }
}
