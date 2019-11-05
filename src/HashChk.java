
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.security.*;
import org.mindrot.jbcrypt.BCrypt;

class HashChk extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Table tablsql = new Table();
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
    private String sqlpass;
    private String check_pass = "hello";

    public static void main(String[] args){
        HashChk chek = new HashChk();
        chek.chk_pass();
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

    private void chk_pass(){
        try{
            puname = "hello";
            
            tablsql.sqlCon();
            usercon = tablsql.con;

            Statement userstat = usercon.createStatement();
            ResultSet userres = userstat.executeQuery("SELECT * FROM customer_info.users WHERE username = '"+ puname +"';");

            while(userres.next()){
                sqlpass = userres.getString(3);
            }

            check_password(check_pass, sqlpass);
        }catch(SQLException el){
            JOptionPane.showMessageDialog(null,"could not access user database");
            txuser.setText("");
            pass.setText("");
            txuser.requestFocus();
        }
    }
}