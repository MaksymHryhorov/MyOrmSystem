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

        connection.withConnection(conn -> {
            process(conn);
            return null;
        });
    }

    private static void process(Connection connection) {
        File json = new File("src/main/resources/format.json");
        File csv = new File("src/main/resources/sample.csv");
        WriteToDatabase wtdb = new WriteToDatabase();

        List<Person> result;

        DataReadWriteSource<ResultSet> rw = new ConnectionReadWriteSource(connection);
        DataReadWriteSource<?> jsonFile = new FileReadWriteSource(json);
        DataReadWriteSource<?> csvFile = new FileReadWriteSource(csv);


        result = ORM.readAll(csvFile, Person.class);
        //result = ORM.readAll(jsonFile, Person.class);
        //result = ORM.readAll(rw, Person.class);
        wtdb.writeToDataBase(result, Person.class);

    }

}
