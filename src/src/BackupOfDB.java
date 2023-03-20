package src;

import java.io.*;
import java.net.*;
import java.sql.*;

import com.google.gson.Gson;

public class BackupOfDB extends MenuItem{
	 // Define a constant for the API URL
	 private static final String API_URL = "http://universities.hipolabs.com/search?country=";

	 // Define variables for the database credentials
	 static String user = "sa";
	 static String pass = "root";
	 static Connection con = null;

	 // Constructor for the "List of Universities" menu item
    BackupOfDB(){
        this.actionName="Backup Of Data ";
    }
    public void action() {
    	
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
            	try {
            		String urll = "jdbc:sqlserver://localhost:1433;" + "databaseName = myDB;" +
                            "encrypt = true;" + "trustServerCertificate = true";
            		con = DriverManager.getConnection(urll, user, pass);
                    String sql = "INSERT INTO University (state_province, domain, country, web_pages, name, alpha_two_code) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);

                    for (University uni : universities) {
                        pstmt.setString(1, uni.get_state_province());
                        pstmt.setString(2, String.join(",", uni.get_domains()));
                        pstmt.setString(3, uni.getCountry());
                        pstmt.setString(4, String.join(",", uni.get_web_pages()));
                        pstmt.setString(5, uni.get_name());
                        pstmt.setString(6, uni.get_alpha_two_code());
                        pstmt.executeUpdate();
                    }

                    pstmt.close();
                    con.close();
                    System.out.println("BackUp is done ^_^");
                }
                catch(Exception e) {
                    // Handle exceptions
                    e.printStackTrace();
                }
            }

        }
        catch(Exception e) {
            // Handle exceptions
        }
    }
}
