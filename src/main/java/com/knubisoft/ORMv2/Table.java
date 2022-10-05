package com.knubisoft.ORMv2;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Table {

    private final Map<Integer, Map<String, String>> table;

    int size() {
        return table.size();
    }

    /**
     * Get row by index
     * @param row some row from table
     * @return Map(key, value)
     */
    Map<String, String> getTableRowByIndex(int row) {
        Map<String, String> result = table.get(row);
        return result == null ? null : new LinkedHashMap<>(result);
    }
}
