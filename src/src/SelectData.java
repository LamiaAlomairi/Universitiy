package src;
import java.sql.*;
import java.util.*;

public class SelectData extends MenuItem {
    List<String> selectData = new ArrayList<>(); 
    Scanner scan = new Scanner(System.in);
    static boolean loop = true;

    SelectData() {
        this.actionName = "Select Data You Want To Print ";
    }

    public void action() {
        while (loop) {
            try {
                // Display options
                System.out.println("Select data you want to print:");
                System.out.println("0. Done selecting");
                System.out.println("1. State Province ");
                System.out.println("2. Domain ");
                System.out.println("3. Country ");
                System.out.println("4. Web Pages ");
                System.out.println("5. Name ");
                System.out.println("6. Alpha 2 code ");

                // Get user input
                int option = scan.nextInt();

                // Handle user input
                switch (option) {
                    case 1:
                        selectData.add("state_province");
                        break;
                    case 2:
                        selectData.add("domain");
                        break;
                    case 3:
                        selectData.add("country");
                        break;
                    case 4:
                        selectData.add("web_pages");
                        break;
                    case 5:
                        selectData.add("name");
                        break;
                    case 6:
                        selectData.add("alpha_two_code");
                        break;
                    case 0:
                        loop = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Print selected data
        if (!selectData.isEmpty()) {
            try {
                // Set up connection to the database
                String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=myDB;" + "encrypt=true;"
                        + "trustServerCertificate=true";
                Connection conn = DriverManager.getConnection(url, "sa", "root");
                Statement stmt = conn.createStatement();

                // Build SQL query
                StringBuilder sb = new StringBuilder("SELECT ");
                for (int i = 0; i < selectData.size(); i++) {
                    sb.append(selectData.get(i));
                    if (i < selectData.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(" FROM University");

                // Execute SQL query
                ResultSet rs = stmt.executeQuery(sb.toString());

                // Get metadata for result set
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumns = metaData.getColumnCount();
                String[] columnNames = new String[numColumns];
                for (int i = 0; i < numColumns; i++) {
                    columnNames[i] = metaData.getColumnName(i + 1);
                }

                // Print header row
                for (int i = 0; i < numColumns; i++) {
                    System.out.printf("%20s",columnNames[i] + "\t");
                }
                System.out.println();

                // Print data rows
                while (rs.next()) {
                    for (int i = 0; i < numColumns; i++) {
                        String data = rs.getString(i + 1);
                        System.out.printf("%20s",data + "\t");
                    }
                    System.out.println();
                }

                // Close the database connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }
}
