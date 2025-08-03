package com.st.hospitalmanagement.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital?continueBatchOnError=false";
    private static final String USER = "steve";
    private static final String PASSWORD = "R697932976d";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Driver MySQL non trouvé");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
