package com.project.ORMv2.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "person")
public class Person implements Serializable {

    private String name;
    private BigInteger age;
    private BigInteger salary;
    private String position;
    private LocalDate dateOfBirth;
    private Float xxx;
}
