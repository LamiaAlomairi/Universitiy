package src;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

public class ListOfCountry extends MenuItem {

    // Use constants for the database credentials to avoid hardcoding them in the code
    static final String DB_USER = "sa";
    static final String DB_PASSWORD = "root";

    // The URL for the API to retrieve a list of universities by country
    static final String API_URL = "http://universities.hipolabs.com/search?country=";

    // A connection to the database (not currently used in this class)
    static Connection dbConnection;

    // The currently selected country
    static String selectedCountry;

    public ListOfCountry() {
        this.actionName = "List Of Countries";
    }

    @Override
    public void action() {

        // Fetch a list of countries from the API
        List<String> countries = fetchCountryListFromApi();

        // Display the list of countries to the user
        displayCountryList(countries);

        // Prompt the user to select a country
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of a country to see its list of universities: ");
        int countryIndex = scanner.nextInt();

        // Ensure that the user input is valid
        while (countryIndex < 1 || countryIndex > countries.size()) {
            System.out.println("Invalid input. Please try again.");
            countryIndex = scanner.nextInt();
        }

        // Set the selected country based on the user's input
        selectedCountry = countries.get(countryIndex - 1);
    }

    /**
     * Fetches a list of countries from the API.
     */
    private static List<String> fetchCountryListFromApi() {
        List<String> countries = new ArrayList<>();

        try {
            // Create a connection to the API and set the request method and headers
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Check if the response code indicates an error and throw an exception if so
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to retrieve data from the API. HTTP error code: " + conn.getResponseCode());
            }

            // Read the response from the API and parse the JSON into an array of universities
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder json = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                json.append(output);
            }

            conn.disconnect();

            Gson gson = new Gson();
            University[] universities = gson.fromJson(json.toString(), University[].class);

            // Extract the unique countries from the list of universities
            for (University university : universities) {
                String country = university.getCountry();
                if (!countries.contains(country)) {
                    countries.add(country);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

    /**
     * Displays a list of countries to the user.
     */
    private static void displayCountryList(List<String> countries) {
        System.out.println("List of countries:");
        for (int i = 0; i < countries.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, countries.get(i));
        }
    }

}
