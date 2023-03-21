package src;

import java.sql.*;

public class RemoveTable extends MenuItem{
	RemoveTable(){
        this.actionName="Remove Table From DB ";
    }
	public void action() {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName = " + Main.db_name +";" + "encrypt = true;" + "trustServerCertificate = true";
    	String user = "sa";
        String pass = "root";
        Connection con = null;
        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            String sql_Delete = "DROP TABLE University;";
            st.executeUpdate(sql_Delete);
            System.out.println("Table is drop ");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
	}
}
