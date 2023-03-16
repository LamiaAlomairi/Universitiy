package src;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;

public class ListOfCountry extends MenuItem{
	static String user = "sa";
    static String pass = "root";
    static Connection con = null;
    static String selectedCountry;
	ListOfCountry(){
        this.actionName="List Of Countries ";
    }
	
	
	
    
	public void action() {
		
		
		// Fetch list of countries from the API
        List<String> countries = new ArrayList<>();
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

            Gson gson = new Gson();
            University[] universities = gson.fromJson(json.toString(), University[].class);

            for (University university : universities) {
                String country = university.getCountry();
                if (!countries.contains(country)) {
                    countries.add(country);
                }
            }

            System.out.println("List of countries:");
            for (int i = 0; i < countries.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, countries.get(i));
            }

            // Prompt user to select a country
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of a country to see its list of universities: ");
            int countryIndex = scanner.nextInt();

            if (countryIndex < 1 || countryIndex > countries.size()) {
                System.out.println("Invalid input.");
                return;
            }

            selectedCountry = countries.get(countryIndex - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}
}
