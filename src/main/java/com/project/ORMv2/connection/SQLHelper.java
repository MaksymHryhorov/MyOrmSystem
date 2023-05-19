package com.project.ORMv2.connection;

import com.project.ORMv2.model.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLHelper {

    @RequiredArgsConstructor
    static class ModelSQLHelper {

        private final List<String> availableFieldInDatabase;

        /**
         * Build sql query to insert values in a database
         * @param o object model
         * @return built query
         */
        public String buildSQL(Object o) {

            Class<? extends Object> cls = o.getClass();
            String tableName = getTableName(cls);
            String fields = getFields(cls);
            String arguments = getArguments(cls);

            return String.format("INSERT INTO %s (%s) VALUES (%s);",
                    tableName, fields, arguments);
        }

        /**
         * Get arguments
         * @param cls unknown class
         * @return list ?
         */
        private String getArguments(Class<?> cls) {

            List<Field> fields = Arrays.asList(cls.getDeclaredFields());
            List<String> listFieldNames = fields.stream().map(Field::getName)
                    .filter(availableFieldInDatabase::contains).map(field -> "?").
                    collect(Collectors.toList());

            return String.join(",", listFieldNames);
        }

        /**
         * Get fields from class
         * @param cls unknown class
         * @return fields
         */
        private String getFields(Class<?> cls) {

            List<Field> fields = Arrays.asList(cls.getDeclaredFields());
            List<String> listFieldNames = fields.stream().map(Field::getName)
                    .filter(availableFieldInDatabase::contains).
                    collect(Collectors.toList());

            return String.join(",", listFieldNames);
        }

        /**
         * Get table name from annotation
         * @param cls unknown cls
         * @return annotation value
         */
        private String getTableName(Class<?> cls) {

            return cls.getAnnotation(Table.class).name();
        }

        /**
         * Bind arguments
         * @param o field - value
         * @param ps sql query
         */
        @SneakyThrows
        public void bindArguments(Object o, PreparedStatement ps) {

            int index = 1;
            for (Field field : o.getClass().getDeclaredFields()) {
                if (availableFieldInDatabase.contains(field.getName())) {
                    field.setAccessible(true);
                    ps.setObject(index, field.get(o));
                    index++;
                }
            }
        }
    }
}
