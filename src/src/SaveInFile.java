package src;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;

public class SaveInFile extends MenuItem{
    private static final String API_URL = "http://universities.hipolabs.com/search?country=";

    SaveInFile(){
        this.actionName="Save Data In File ";
    }
    public void action() {
        try {
            // Fetch list of universities for the selected country
            String apiUrl = API_URL + ListOfCountry.selectedCountry;
            URL url = new URL(apiUrl);
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

            if (universities.length == 0) {
                System.out.println("No universities available in the selected country");
            }
            else {
                try (FileWriter files = new FileWriter("University.txt")) {
                    files.write("List of universities in " + ListOfCountry.selectedCountry + ":\n");
                    files.write("state_province     domain      country                       web pages                                  name               alpha two code\n");
                    for (int i = 0; i < universities.length; i++) {
                        files.write(universities[i].get_state_province()+"           "+universities[i].get_domains()+"         "+universities[i].getCountry()+"     "+universities[i].get_web_pages()+"     "+universities[i].get_name()+"     "+universities[i].get_alpha_two_code()+"\n");
                    }
                    System.out.println("Data written to file successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the file.");
                    //e.printStackTrace();
                }
            }
        }
        catch(Exception e) {
            // Handle exceptions
        }
    }
}
