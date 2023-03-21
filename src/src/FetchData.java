package src;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

public class FetchData extends MenuItem{
	// Define a constant for the API URL
	 private static final String API_URL = "http://universities.hipolabs.com/search?country=";

	 // Define variables for the database credentials
	 static String user = "sa";
	 static String pass = "root";
	 static Connection con = null;

	FetchData(){
        this.actionName="Fetch Data From (API or DB) ";
    }
	public void action() {
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("1. Fetch Data Frome API ");
			System.out.println("2. Fetch Data Frome BataBase ");
			System.out.println("Select from where you want to fetch data ");
			int fetchData = scan.nextInt();
			if(fetchData == 1) {
				fetchFromAPI();
			}
			else if(fetchData == 2) {
				fetchFromDB();
			}
		}
		catch(Exception e) {
			System.out.println("Wrong Number 0_0");
		}
	}
	
	public void fetchFromAPI() {
		try {
	         // Fetch list of countries from the API
	         URL url = new URL("http://universities.hipolabs.com/search?country=");
	         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         conn.setRequestProperty("Accept", "application/json");

	         if (conn.getResponseCode() != 200) {
	             throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	         }

	         BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	         String output;
	         StringBuilder json = new StringBuilder();

	         while ((output = br.readLine()) != null) {
	             json.append(output);
	         }

	         conn.disconnect();

	         // Fetch list of universities for the selected country
	         String apiUrl = API_URL + ListOfCountry.selectedCountry;
	         url = new URL(apiUrl);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         conn.setRequestProperty("Accept", "application/json");

	         if (conn.getResponseCode() != 200) {
	             throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	         }

	         br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	         json = new StringBuilder();

	         while ((output = br.readLine()) != null) {
	             json.append(output);
	         }

	         conn.disconnect();

	         // Parse the JSON data and display the list of universities
	         Gson gson = new Gson();
	         University[] universities = gson.fromJson(json.toString(), University[].class);

	         if(universities.length == 0) {
	             System.out.println("No universities available in the selected country ");
	         }
	         else {
	             System.out.println("List of universities in " + ListOfCountry.selectedCountry + ":");
	             for (int i = 0; i < universities.length; i++) {
	                 System.out.printf("%d. %s%n", i + 1, universities[i].get_name());
	             }
	         }

	     }
	     catch(Exception e) {
	         // Handle exceptions
	     }
	}
	
	public void fetchFromDB() {
		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName = " + Main.db_name +";" + "encrypt = true;" + "trustServerCertificate = true";
			String user = "sa";
	        String pass = "root";
	        Connection con = null;
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            String sql1 = "Select * from University";
            ResultSet resultSet = st.executeQuery(sql1);
            int i = 1;
            System.out.println("______________________________________________________________________________________________________________________________________________________________________________ ");
        	System.out.printf("%5s %20s %20s %20s %60s %20s %10s","No","state province ",
        			"domain","country ","web pages","name","alpha two code");
        	System.out.println();
        	System.out.println("______________________________________________________________________________________________________________________________________________________________________________ ");
        	while (resultSet.next()) {
            	System.out.printf("%5s %20s %20s %20s %60s %20s %10s",i,resultSet.getString("state_province"),resultSet.getString("domain"),resultSet.getString("country"),resultSet.getString("web_pages"),resultSet.getString("name"),resultSet.getString("alpha_two_code"));
            	System.out.println();
            	i++;
            	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ");
            }

            System.out.println("______________________________________________________________________________________________________________________________________________________________________________ ");
            con.close();
		} catch (Exception ex) {
            System.err.println(ex);
        }
	}
}
