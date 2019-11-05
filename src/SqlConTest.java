import java.sql.*;

class SqlConTest {
    public String url = "jdbc:mysql://192.168.1.150:3306/test";
    public String user = "autobase";
    public String password = "!1Swamigee";

    int print;
    String printint;

    public void connect() throws SQLException{
        Connection con = DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM testDB");

        while(rs.next()){
            print = rs.getInt(1);
        }
        printint = String.valueOf(print);
        System.out.println(printint);
    }

    public static void main(String[] args) throws SQLException{
        SqlConTest sql = new SqlConTest();
        sql.connect();
    }
}