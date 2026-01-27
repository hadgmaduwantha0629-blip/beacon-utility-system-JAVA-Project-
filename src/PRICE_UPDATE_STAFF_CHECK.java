
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gevindu
 * this is for ticket price update process-BUS
 */

 class PRICE_UPDATE_STAFF_CHECK extends Main_Staff_Check{
    @Override
    public void set_action(String action){
    get_action = action;
    }

    @Override
    public void staff_check(){
     String jdbcUrl = "jdbc:mysql://localhost:3306/BUS"; // Replace with your database name
    String username = "root"; // Replace with your MySQL username
    String password = ""; // Replace with your MySQL password

    try {
        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the database!");

            // Prepare SQL query to check if NIC and password are correct
            String OFFICER_NIC = OFFICERNIC.getText();
            String OFFICER_PASSWORD = new String(OFFICERPW.getPassword());

            String sqlQuery = "SELECT * FROM STAFF WHERE ONIC = ? AND OPASSWORD = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                // Set parameters
                preparedStatement.setString(1, OFFICER_NIC);
                preparedStatement.setString(2, OFFICER_PASSWORD);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // NIC and password found, login successfully
                        System.out.println("NIC and PASSWORD found in the database. Login successful.");
                        
                        new UPDATE_TICKET_PRICE().setVisible(true);
                    } else {
                        // NIC or password not found
                        System.out.println("Invalid Staff ID or PASSWORD.");
                        javax.swing.JOptionPane.showMessageDialog(this, "Invalid STAFF ID or PASSWORD.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        }
    } catch (ClassNotFoundException e) {
        System.out.println("MySQL JDBC Driver not found.");
        e.printStackTrace();
    }
    }
}   

