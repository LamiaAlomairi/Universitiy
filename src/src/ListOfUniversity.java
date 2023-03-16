package src;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import com.google.gson.Gson;

public class ListOfUniversity extends MenuItem{
	private static final String API_URL = "http://universities.hipolabs.com/search?country=";
	static String user = "sa";
    static String pass = "root";
    static Connection con = null;
	ListOfUniversity(){
        this.actionName="List Of Universities ";
    }
	public void action() {
		try {
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

            Gson gson = new Gson();
            University[] universities = gson.fromJson(json.toString(), University[].class);

            System.out.println("List of universities in " + ListOfCountry.selectedCountry + ":");
            for (int i = 0; i < universities.length; i++) {
                System.out.printf("%d. %s%n", i + 1, universities[i].get_name());
            }
		}
		catch(Exception e) {
			
		}
	}
}
