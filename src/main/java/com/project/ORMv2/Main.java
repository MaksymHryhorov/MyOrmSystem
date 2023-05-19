package com.project.ORMv2;

import com.project.ORMv2.mapper.ORM;
import com.project.ORMv2.model.Person;
import com.project.ORMv2.sourceInterf.DataReadWriteSource;
import com.project.ORMv2.sourceInterf.ORMInterface;
import com.project.rwsouce.FileReadWriteSource;

import java.io.File;
import java.util.List;


public class Main {

    private static final ORMInterface ORM = new ORM();


    public static void main(String[] args) {
        File fileFormat = new File("src/main/resources/sample.csv");
        DataReadWriteSource<?> source = new FileReadWriteSource(fileFormat);
        List<Person> result = ORM.readAll(source, Person.class);
        result.forEach(System.out::println);
    }

}
