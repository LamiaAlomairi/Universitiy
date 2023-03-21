package src;
import java.sql.*;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static boolean initial_loop = true;
    static String db_name;
    static String user = "sa";
    static String pass = "root";
    static Connection con = null;
    static boolean project = true;
    static ArrayList<MenuItem> menuList = new ArrayList<MenuItem>(); 
//  **************************************************************************************************************************
//  **********************   getter and setter    ****************************************************************************    
//  **************************************************************************************************************************
    public static void setDBName(String db_name) {
        Main.db_name = db_name;
    }

    public static String getDBName() {
        return db_name;
    }
//  **************************************************************************************************************************
//  **********************       Main Method      ****************************************************************************    
//  **************************************************************************************************************************    
    public static void main(String[] args) {
    	SelectData.loop = true;
        initial_database();
        createTable();
    	Array();
        while(project) {
        	try {
        		System.out.printf("%30s%n","_______________________________________");
        		System.out.printf("%17s %11s%n","Menu Option","");
        		System.out.printf("%30s%n","---------------------------------------");
        		for (int i = 0; i < menuList.size(); i++){
        			System.out.println("    "+(i+1) + "- " + menuList.get(i).actionName);
                }
        		System.out.printf("%30s%n","_______________________________________");
        		System.out.println();
        		
                System.out.print("Enter Number of Option: ");
                int option = scan.nextInt();
                System.out.printf("%30s%n","_____________________________");
                if(option >0 && option <= menuList.size()) {
                	option = option - 1;
                	menuList.get(option).action();	   	 
	 			    }
	 			    else {
	 			    	System.out.println(" ________________________________________________");
	 			    	System.out.println("|   Invalid number, please enter a valid number  |");
	 			    	System.out.println("|________________________________________________|");
	 			    }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

   
//  **************************************************************************************************************************
//  **********************     Create Database    ****************************************************************************    
//  **************************************************************************************************************************
    static void initial_database() {
    	String url = "jdbc:sqlserver://localhost:1433;" + "encrypt = true;" + "trustServerCertificate = true";
        try {
            while (initial_loop) {
                System.out.print("Enter DataBase Name: ");
                db_name = scan.next();

                Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                DriverManager.registerDriver(driver);
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement();
                String sql = "CREATE DATABASE " + db_name +";";
                statement.executeUpdate(sql);
                initial_loop = false;
            }
        } 
        catch (Exception e) {
            System.out.println("Some Error Happened >_< ");
        }
    }
    
//  **************************************************************************************************************************
//  **********************      Create Table      ****************************************************************************    
//  **************************************************************************************************************************    
    static void createTable() {
    	String url = "jdbc:sqlserver://localhost:1433;" + "databaseName =" + db_name +";" + "encrypt = true;" + "trustServerCertificate = true";
    	String user = "sa";
		String pass = "root";
	    Connection con = null;
        try {
        	Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);

            Statement st = con.createStatement();

            String sql_university= "IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'University') "
                    + "CREATE TABLE University(\r\n"
                    + "    state_province VARCHAR(20),\r\n"
                    + "    domain VARCHAR(200),\r\n"
                    + "    country VARCHAR(20),\r\n"
                    + "    web_pages VARCHAR(500),\r\n"
                    + "    name VARCHAR(20), \r\n"
                    + "    alpha_two_code CHAR(2) \r\n"
                    + ");";
            st.executeUpdate(sql_university);
            con.close();
        }
	        catch (Exception ex) {
	            System.out.println("Something Error Happened -_- ");
	        }		
			
		}
    
//  **************************************************************************************************************************
//  **********************    Store Menu Item     ****************************************************************************    
//  **************************************************************************************************************************   
    public static void Array()
    {
    	menuList.add(new ListOfCountry());
    	menuList.add(new ListOfUniversity());
    	menuList.add(new BackupOfDB());
    	menuList.add(new FetchData());
    	menuList.add(new SelectData());
    	menuList.add(new SaveInFile());
    	menuList.add(new FetchDataFromFile());
    	menuList.add(new RemoveTable());
	    menuList.add(new Exit());
    }
	
}
