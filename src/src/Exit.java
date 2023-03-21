package src;
import java.sql.*;

public class Exit extends MenuItem{
	Exit(){
        this.actionName="Exit ";
    }
	public void action() {
		Main.project = false;
		System.out.println("Program is stoped ");
		System.out.println("Bye o_- ");
		
		String url = "jdbc:sqlserver://localhost:1433;" + "encrypt = true;" + "trustServerCertificate = true";
    	String user = "sa";
        String pass = "root";
        Connection con = null;
        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            String sql_DeleteDB = "DROP DATABASE " + Main.db_name + ";";
            st.executeUpdate(sql_DeleteDB);
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        
	}
}
