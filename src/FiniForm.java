/*import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class FiniForm extends JFrame{
    private static final long serialVersionUID = 1L;

  /*  protected JPanel panel;
    protected JButton fsubmit;
    protected JTextField odoread;
    protected JLabel lblodoread;
    public String odoreturn;

    public void Form() {
        Font font = new Font("serif", Font.PLAIN, 18);

        panel = new JPanel();
        fsubmit = new JButton("Submit");

        odoread = new JTextField();

        Submit sbmt = new Submit();
        odoread.addActionListener(sbmt);

        lblodoread = new JLabel("Odometer Reading:");

        setSize(500, 300);
        setLocation(500, 280);
        panel.setLayout(null);

        odoread.setBounds(50, 50, 400, 30);
        fsubmit.setBounds(150, 200, 200, 40);
        odoread.setFont(font);
        lblodoread.setFont(font);
        lblodoread.setBounds(50, 15, 200, 40);

        panel.add(fsubmit);
        panel.add(odoread);
        panel.add(lblodoread);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public class Submit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            MysqlCon sql = new MysqlCon();

            Connection con;

            odoreturn = odoread.getText();

            try {
                String carsql = "INSERT INTO `customer_info`.`car-info` (`odometer reading on return`) VALUES (?);";

                con = DriverManager.getConnection(sql.url, sql.user, sql.password);
                PreparedStatement pstmt = con.prepareStatement(carsql);
                pstmt.setString(1, odoreturn);
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
}*/