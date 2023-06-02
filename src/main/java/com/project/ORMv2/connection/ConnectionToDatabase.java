package com.project.ORMv2.connection;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.function.Function;

public class ConnectionToDatabase {

    @SneakyThrows
    public void withConnection(Function<Connection, Void> function) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS person " +
                        "(id INTEGER not NULL, " +
                        " name VARCHAR(255), " +
                        " position VARCHAR(255), " +
                        " age INTEGER, " +
                        " PRIMARY KEY ( id ))");

                stmt.executeUpdate("DELETE FROM person");
            }
            function.apply(c);
        }
    }

}
