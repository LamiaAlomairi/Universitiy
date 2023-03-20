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
		
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName = myDB" /*+ Main.db_name */+";" + "encrypt = true;" + "trustServerCertificate = true";
    	String user = "sa";
        String pass = "root";
        Connection con = null;
        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            //String sql_AlterDB = "ALTER DATABASE "+Main.db_name+" SET SINGLE_USER WITH ROLLBACK IMMEDIATE;";
            //st.executeUpdate(sql_AlterDB);
            //String sql_DeleteDB = "DROP DATABASE "+Main.db_name+";";
            String sql_DeleteDB = "DROP TABLE University;";
            st.executeUpdate(sql_DeleteDB);
        } catch (Exception ex) {
            System.err.println(ex);
        }
        
	}
}
