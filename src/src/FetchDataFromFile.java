package src;

import java.io.*;
import java.net.*;
import java.util.*;

public class FetchDataFromFile extends MenuItem{
	FetchDataFromFile(){
        this.actionName="Fetch Data From File ";
    }
	public void action() {
		 try {
	            BufferedReader br = new BufferedReader(new FileReader("University.txt"));
	            String line = br.readLine(); // Skip first line
	            ArrayList<University> universities = new ArrayList<>();

	            while ((line = br.readLine()) != null) {
	                String[] values = line.split("\\s+");
	                University university = new University();
	                university.set_state_province(values[0]);
	                university.set_domains(values[1]);
	                university.setCountry(values[2]);
	                university.set_web_pages(values[3]);
	                university.set_name(values[4]);
	                university.set_alpha_two_code(values[5]);
	                universities.add(university);
	            }

	            br.close();

	            // Print universities list
	            for (University university : universities) {
	                System.out.println(university.get_name() + ", " + university.getCountry());
	            }
	        } catch (IOException e) {
	            System.out.println("An error occurred while reading the file.");
	            //e.printStackTrace();
	        }
	}
}
