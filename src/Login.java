import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        Login frameTable = new Login();
        frameTable.Log();
    }

    protected JButton blogin;
    protected JPanel panel;
    protected JTextField txuser;
    protected JPasswordField pass;
    protected JLabel l1;
    protected JLabel l2;

    public void Log(){
      
        l1=new JLabel("Username");  
        l2=new JLabel("Password"); 
        blogin = new JButton("login");
        panel = new JPanel();
        txuser = new JTextField();
        pass = new JPasswordField();
        pass.addActionListener(this);
       
        setSize(300,300);
        setLocation(500,280);
        panel.setLayout(null);

        l1.setBounds(70,45, 100, 30); 
        l2.setBounds(70,80, 100,30); 
        txuser.setBounds(70,70,150,20);
        pass.setBounds(70,105,150,20);
        blogin.setBounds(110,140,80,20);

        panel.add(blogin);
        panel.add(txuser);
        panel.add(pass);
        panel.add(l1);
        panel.add(l2);
 
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }   

            public void actionPerformed(ActionEvent e){
                String puname = txuser.getText();
                String ppaswd = pass.getText();
                if(puname.equals("admin") && ppaswd.equals("admin")){
                    System.out.println("correct");
                }else{
                    JOptionPane.showMessageDialog(null,"wrong Password / Username");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }
            }

}
