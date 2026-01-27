/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.*;

public class DatabaseManager {
    // Encapsulated database connection details
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private Connection connection;

    // Constructor for initializing database connection details
    public DatabaseManager(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    // Method to establish a connection
    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Connected to the database!");
    }

    // Method to close the connection
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }

    // Method to check if NIC exists in the database
    public boolean doesNICExist(String registeredNIC) throws SQLException {
        String sqlQuery = "SELECT NIC FROM PASSWORD WHERE NIC = ? " +
                          "UNION " +
                          "SELECT ONIC FROM STAFF WHERE ONIC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, registeredNIC);
            preparedStatement.setString(2, registeredNIC);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Method to update the password in the PASSWORD table
    public boolean updatePasswordTable(String registeredNIC, String newPassword) throws SQLException {
        String sqlUpdatePassword = "UPDATE PASSWORD SET PASSWORD = ? WHERE NIC = ?";
        try (PreparedStatement updatePasswordStmt = connection.prepareStatement(sqlUpdatePassword)) {
            updatePasswordStmt.setString(1, newPassword);
            updatePasswordStmt.setString(2, registeredNIC);
            return updatePasswordStmt.executeUpdate() > 0;
        }
    }

    // Method to update the password in the STAFF table
    public boolean updateStaffTable(String registeredNIC, String newPassword) throws SQLException {
        String sqlUpdateStaff = "UPDATE STAFF SET OPASSWORD = ? WHERE ONIC = ?";
        try (PreparedStatement updateStaffStmt = connection.prepareStatement(sqlUpdateStaff)) {
            updateStaffStmt.setString(1, newPassword);
            updateStaffStmt.setString(2, registeredNIC);
            return updateStaffStmt.executeUpdate() > 0;
        }
    }
}