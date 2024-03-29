package com.project.ORMv2.parsingStrategy;

import com.project.ORMv2.connection.ConnectionReadWriteSource;
import com.project.ORMv2.mapper.Table;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseParsingStrategy implements ParsingStrategy<ConnectionReadWriteSource> {
    private final Class<?> tableClass;

    public DatabaseParsingStrategy(Class<?> clazz) {
        this.tableClass = clazz;
    }

    /**
     * Table
     * @param connection to a database
     * @return table - Map(key, value)
     */
    @SneakyThrows
    @Override
    public Table parseToTable(ConnectionReadWriteSource connection) {
        Statement statement = connection.getContent().createStatement();

        String table = tableClass.getAnnotation(com.project.ORMv2.model.Table.class).name();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + table);
        Map<Integer, Map<String, String>> result = buildTable(rs);

        return new Table(result);
    }

    /**
     * Build Table
     * @param rs result set
     * @return Map(key, Map(key, value))
     */
    @SneakyThrows
    private Map<Integer, Map<String, String>> buildTable(ResultSet rs) {
        ResultSetMetaData metadata = rs.getMetaData();

        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();
        int rowId = 0;
        while (rs.next()) {
            Map<String, String> row = new LinkedHashMap<>();
            for (int index = 1; index <= metadata.getColumnCount(); index++) {
                row.put(metadata.getColumnName(index), rs.getString(index));
            }
            result.put(rowId, row);
            rowId++;
        }

        return result;
    }
}
