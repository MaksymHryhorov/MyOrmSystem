package com.knubisoft.ORMv2;

import com.knubisoft.ORMv2.model.Person;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private static final Parser parser = new Parser();
    @Test
    void process() {
        ConnectionToDatabase connection = new ConnectionToDatabase();
        List<Person> personList = List.of(new Person(
                "Igor",
                BigInteger.valueOf(18),
                BigInteger.valueOf(2000),
                "middle",
                LocalDate.of(1997, 10, 10),
                1234.123f));

        List<Person> personList1 = List.of(new Person(
                "Ivan",
                BigInteger.valueOf(19),
                BigInteger.valueOf(1000),
                "junior",
                LocalDate.of(1995, 10, 19),
                1234.123f));

        connection.withConnection(conn -> {
            assertEquals(personList.get(0).toString(), parser.process(conn).get(0).toString());
            assertEquals(personList1.get(0).toString(), parser.process(conn).get(1).toString());

            return null;
        });

    }
}