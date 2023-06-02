package com.project.ORMv2.connection;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class WriteToDatabase {

    /**
     * Write model to a database
     *
     * @param list model with filled values
     */
    @SneakyThrows
    public void writeToDataBase(List<?> list, Class<?> cls) {
        ConnectionToDatabase connection = new ConnectionToDatabase();
        connection.withConnection(conn -> {
            for (Object object : list) {
                SQLHelper.ModelSQLHelper helper = new SQLHelper.ModelSQLHelper(collectMetaInformation(object));
                String sql = helper.buildSQL(object);
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    helper.bindArguments(object, ps);
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
    }

    private List<String> collectMetaInformation(Object objectToInsert) {
        List<String> metadata = new ArrayList<>();
        Field[] fields = objectToInsert.getClass().getDeclaredFields();
        for (Field field : fields) {
            metadata.add(field.getName());
        }
        return metadata;
    }
}
