package src;

import java.io.*;

public class FetchDataFromFile extends MenuItem{
	FetchDataFromFile(){
        this.actionName="Fetch Data From File ";
    }
	public void action() {
		
		String fileName = "University.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        	 System.out.println("An error occurred while reading the file.");
        }
	}
}
