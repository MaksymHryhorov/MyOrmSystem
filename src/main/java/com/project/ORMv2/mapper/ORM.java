package com.project.ORMv2.mapper;

import com.project.ORMv2.connection.ConnectionReadWriteSource;
import com.project.ORMv2.parsingStrategy.*;
import com.project.ORMv2.sourceInterf.DataReadWriteSource;
import com.project.ORMv2.sourceInterf.ORMInterface;
import com.project.rwsouce.FileReadWriteSource;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ORM implements ORMInterface {

    /**
     * Filled Model list
     * @param inputSource connection
     * @param cls unknown class
     * @param <T> unknown type
     * @return Model List
     */
    @Override
    @SneakyThrows
    public <T> List<T> readAll(DataReadWriteSource<?> inputSource, Class<T> cls) {
        Table table = convertToTable(inputSource, cls);
        return convertTableToListOfClasses(table, cls);
    }

    /**
     * Fill values into model fields
     * @param table Table - Map(key, value)
     * @param cls unknown class
     * @param <T> unknown type
     * @return Model list
     */
    private <T> List<T> convertTableToListOfClasses(Table table, Class<T> cls) {
        List<T> result = new ArrayList<>();
        for (int index = 0; index < table.size(); index++) {
            Map<String, String> row = table.getTableRowByIndex(index);
            T instance = reflectTableRowToClass(row, cls);
            result.add(instance);
        }
        return result;
    }

    /**
     * Set up values into model class
     * @param row key, value
     * @param cls unknown class
     * @param <T> unknown type
     * @return cls instance
     */
    @SneakyThrows
    private <T> T reflectTableRowToClass(Map<String, String> row, Class<T> cls) {
        T instance = cls.getDeclaredConstructor().newInstance();
        for (Field each : cls.getDeclaredFields()) {
            each.setAccessible(true);
            String value = row.get(each.getName());
            if (value != null) {
                each.set(instance, transformValueToFieldType(each, value));
            }
        }
        return instance;
    }

    /**
     * Fill values into fields
     * @param field from unknown class
     * @param value to fill field
     * @return Object
     */
    private static Object transformValueToFieldType(Field field, String value) {
        Map<Class<?>, Function<String, Object>> typeToFunction = new LinkedHashMap<>();
        typeToFunction.put(String.class, s -> s);
        typeToFunction.put(int.class, Integer::parseInt);
        typeToFunction.put(Float.class, Float::parseFloat);
        typeToFunction.put(LocalDate.class, LocalDate::parse);
        typeToFunction.put(LocalDateTime.class, LocalDate::parse);
        typeToFunction.put(Long.class, Long::parseLong);
        typeToFunction.put(BigInteger.class, BigInteger::new);

        return typeToFunction.getOrDefault(field.getType(), type -> {
            throw new UnsupportedOperationException("Type isn't supported by parser " + type);
        }).apply(value);
    }

    /**
     * Convert data to table
     * @param dataInputSource connection
     * @param cls unknown
     * @return Table (LinkedHashMap(key, value))
     */
    private Table convertToTable(DataReadWriteSource dataInputSource, Class<?> cls) {
        if (dataInputSource instanceof ConnectionReadWriteSource) {
            ConnectionReadWriteSource databaseSource = (ConnectionReadWriteSource) dataInputSource;

            return new DatabaseParsingStrategy(cls).parseToTable(databaseSource);
        } else if (dataInputSource instanceof FileReadWriteSource) {
            FileReadWriteSource fileSource = (FileReadWriteSource) dataInputSource;
            return getStringParsingStrategy(fileSource).parseToTable(fileSource);
        } else {
            throw new UnsupportedOperationException("Unknown DataInputSource " + dataInputSource);
        }
    }

    /**
     * Choose parse strategy
     * @param inputSource file
     * @return parse strategy
     */
    private ParsingStrategy<FileReadWriteSource> getStringParsingStrategy(FileReadWriteSource inputSource) {
        String content = inputSource.getContent();
        char firstChar = content.charAt(0);

        switch (firstChar) {
            case '{':
            case '[':
                return new JSONParsingStrategy();
            case '<':
                return new XMLParsingStrategy();
            default:
                return new CSVParsingStrategy();
        }
    }

}