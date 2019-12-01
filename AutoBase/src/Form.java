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

    protected JTextField year;
    protected JTextField make;
    protected JTextField model;
    protected JTextField vin;
    protected JTextField licence;
    protected JTextField odoread;

    protected JLabel lblyear;
    protected JLabel lblmake;
    protected JLabel lblmodel;
    protected JLabel lblvin;
    protected JLabel lbllicence;
    protected JLabel lblodoread;

    public String years;
    public String makes;
    public String models;
    public String vins;
    public String licences;
    public String odoreads;

    public void Data() {
        Font font = new Font("serif", Font.PLAIN, 18);

        panel = new JPanel();
        fname = new JTextField();
        lname = new JTextField();
        email = new JTextField();
        phone = new JTextField();
        home = new JTextField();
        submit = new JButton("Submit");

        year = new JTextField();
        make = new JTextField();
        model = new JTextField();
        vin = new JTextField();
        licence = new JTextField();
        odoread = new JTextField();

        Submit sbmt = new Submit();
        odoread.addActionListener(sbmt);
        submit.addActionListener(sbmt);

        lblfname = new JLabel("First Name:");
        lbllname = new JLabel("Last Name:");
        lblemail = new JLabel("Email Address:");
        lblphone = new JLabel("Phone Number:");
        lblhome = new JLabel("Home Address:");

        lblyear = new JLabel("Car Year:");
        lblmake = new JLabel("Car make:");
        lblmodel = new JLabel("Car Model:");
        lblvin = new JLabel("VIN #:");
        lbllicence = new JLabel("Licence Plate:");
        lblodoread = new JLabel("Odometer Reading:");

        setSize(600, 600);
        setLocation(500, 280);
        panel.setLayout(null);

        fname.setBounds(75, 40, 225, 30);
        lname.setBounds(300, 40, 225, 30);
        email.setBounds(75, 100, 450, 30);
        phone.setBounds(75, 160, 450, 30);
        home.setBounds(75, 220, 450, 30);

        year.setBounds(75, 280, 225, 30);
        vin.setBounds(300, 280, 225, 30);
        make.setBounds(75, 340, 450, 30);
        model.setBounds(75, 400, 450, 30);
        licence.setBounds(75, 460, 225, 30);
        odoread.setBounds(300, 460, 225, 30);

        submit.setBounds(200, 520, 200, 40);

        fname.setFont(font);
        lname.setFont(font);
        email.setFont(font);
        phone.setFont(font);
        home.setFont(font);

        year.setFont(font);
        make.setFont(font);
        model.setFont(font);
        vin.setFont(font);
        licence.setFont(font);
        odoread.setFont(font);

        lblyear.setFont(font);
        lblmake.setFont(font);
        lblmodel.setFont(font);
        lblvin.setFont(font);
        lbllicence.setFont(font);
        lblodoread.setFont(font);

        lblfname.setBounds(75, 5, 200, 40);
        lbllname.setBounds(300, 5, 200, 40);
        lblemail.setBounds(75, 65, 200, 40);
        lblphone.setBounds(75, 125, 200, 40);
        lblhome.setBounds(75, 185, 200, 40);

        lblyear.setBounds(75, 245, 200, 40);
        lblmake.setBounds(75, 305, 200, 40);
        lblmodel.setBounds(75, 365, 200, 40);
        lblvin.setBounds(300, 245, 200, 40);
        lbllicence.setBounds(75, 425, 200, 40);
        lblodoread.setBounds(300, 425, 200, 40);

        lblfname.setFont(font);
        lbllname.setFont(font);
        lblemail.setFont(font);
        lblphone.setFont(font);
        lblhome.setFont(font);

        panel.add(fname);
        panel.add(lname);
        panel.add(email);
        panel.add(phone);
        panel.add(home);
        panel.add(submit);

        panel.add(year);
        panel.add(make);
        panel.add(model);
        panel.add(vin);
        panel.add(licence);
        panel.add(odoread);

        panel.add(lblyear);
        panel.add(lblmake);
        panel.add(lblmodel);
        panel.add(lblvin);
        panel.add(lbllicence);
        panel.add(lblodoread);

        panel.add(lblfname);
        panel.add(lbllname);
        panel.add(lblemail);
        panel.add(lblphone);
        panel.add(lblhome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class Submit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            MysqlCon sql = new MysqlCon();

            Connection con;

            name = fname.getText();
            lastname = lname.getText();
            inemail = email.getText();
            inphone = phone.getText();
            inhome = home.getText();

            years = year.getText();
            makes = make.getText();
            models = model.getText();
            vins = vin.getText();
            licences = licence.getText();
            odoreads = odoread.getText();

            try {
                String customersql = "INSERT INTO `info_db`.`info` (`First Name`, `Last Name`, `Email Address`, `Phone Number`, `Home Address`) VALUES (?, ?, ?, ?, ?)";
                String carsql = "INSERT INTO `info_db`.`car-info` (`Last Name`, `Year`, `Make`, `Model`, `VIN #`, `Licence Plate`, `odometer reading on intake`) VALUES (?, ?, ?, ?, ?, ?, ?);";

                con = DriverManager.getConnection(sql.url, sql.user, sql.password);
                PreparedStatement pstmt = con.prepareStatement(customersql);
                pstmt.setString(1, name);
                pstmt.setString(2, lastname);
                pstmt.setString(3, inemail);
                pstmt.setString(4, inphone);
                pstmt.setString(5, inhome);
                pstmt.executeUpdate();

                PreparedStatement prestat = con.prepareStatement(carsql);
                prestat.setString(1, lastname);
                prestat.setString(2, years);
                prestat.setString(3, makes);
                prestat.setString(4, models);
                prestat.setString(5, vins);
                prestat.setString(6, licences);
                prestat.setString(7, odoreads);
                prestat.executeUpdate();

                /*PreparedStatement prest = con.prepareStatement();
                prest.setString(1, lastname);
                prest.setString(2, vins);
                prest.setString(3, licences);
                prest.executeUpdate();*/

                con.close();
                setVisible(false);
                dispose();

                try{
                    Table tbl = new Table();
                    tbl.Wait();
                    tbl.Refresh();
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