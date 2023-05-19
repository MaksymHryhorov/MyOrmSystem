package com.project.ORM.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String dbhost = "jdbc:mysql://localhost:3306/education";
    private static String username = "root";
    private static String password = "root";
    private static Connection conn;

    public static Connection createNewDBConnection() {
        try  {
            conn = DriverManager.getConnection(
                    dbhost, username, password);

        } catch (SQLException e) {
            System.out.println("Cannot create database connection");
            e.printStackTrace();
        }

        return conn;
    }
}
