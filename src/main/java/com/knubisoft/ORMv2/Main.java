package com.knubisoft.ORMv2;

import com.knubisoft.ORMv2.model.Person;
import com.knubisoft.ORMv2.parsingStrategy.ParsingStrategy;
import com.knubisoft.ORMv2.parsingStrategy.XMLParsingStrategy;
import com.knubisoft.ORMv2.sourceInterf.DataReadWriteSource;
import com.knubisoft.ORMv2.sourceInterf.ORMInterface;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;


public class Main {

    private static final ORMInterface ORM = new ORM();


    public static void main(String[] args) {
        ConnectionToDatabase connection = new ConnectionToDatabase();
        Parser parser = new Parser();

        connection.withConnection(conn -> {
            parser.process(conn);
            return null;
        });
    }

}
