package com.knubisoft.ORMv2;

import com.knubisoft.ORMv2.model.Person;
import com.knubisoft.ORMv2.sourceInterf.DataReadWriteSource;
import com.knubisoft.ORMv2.sourceInterf.ORMInterface;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class Parser {
    private static final ORMInterface ORM = new ORM();

    public List<Person> process(Connection connection) {
        File json = new File("src/main/resources/format.json");
        File csv = new File("src/main/resources/sample.csv");

        List<Person> result;

        DataReadWriteSource<ResultSet> rw = new ConnectionReadWriteSource(connection);
        DataReadWriteSource<?> jsonFile = new FileReadWriteSource(json);
        DataReadWriteSource<?> csvFile = new FileReadWriteSource(csv);


        result = ORM.readAll(csvFile, Person.class);

        return result;
        //result = ORM.readAll(jsonFile, Person.class);
        //result = ORM.readAll(rw, Person.class);

        //WriteToDatabase wtdb = new WriteToDatabase();
        //wtdb.writeToDataBase(result, Person.class);

    }
}
