package com.project.ORMv2.parsingStrategy;

import com.project.rwsouce.FileReadWriteSource;
import com.project.ORMv2.mapper.Table;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVParsingStrategy implements ParsingStrategy<FileReadWriteSource> {

    public static final String DELIMITER = ",";
    public static final String COMMENT = "--";

    /**
     * Create new Table from csv file
     * @param content csv file
     * @return Table -> Map(key, value)
     */
    @Override
    public Table parseToTable(FileReadWriteSource content) {
        List<String> lines = Arrays.asList(content.getContent().split(System.lineSeparator()));
        Map<Integer, String> mapping = buildMapping(lines.get(0));
        Map<Integer, Map<String, String>> result = buildTable(lines.subList(1, lines.size()), mapping);
        return new Table(result);
    }

    /**
     * Build table
     * @param lines values from csv file
     * @param mapping headers from csv line
     * @return Map(key, Map(key, value))
     */
    private Map<Integer, Map<String, String>> buildTable(List<String> lines, Map<Integer, String> mapping) {
        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            result.put(index, buildRow(mapping, line));
        }
        return result;
    }

    /**
     * Map headers with values
     * @param mapping headers from csv file
     * @param line values
     * @return Map(key, value)
     */
    private Map<String, String> buildRow(Map<Integer, String> mapping, String line) {
        Map<String, String> nameToValueMap = new LinkedHashMap<>();
        String[] rowItems = splitLine(line);
        for (int rowIndex = 0; rowIndex < rowItems.length; rowIndex++) {
            String value = rowItems[rowIndex];
            nameToValueMap.put(mapping.get(rowIndex), value);
        }
        return nameToValueMap;
    }

    /**
     * Build headers from first csv line
     * @param firstLine line from csv file
     * @return Map (key, value)
     */
    private Map<Integer, String> buildMapping(String firstLine) {
        Map<Integer, String> map = new LinkedHashMap<>();
        String[] array = splitLine(firstLine);
        for (int index = 0; index < array.length; index++) {
            String value = array[index];
            if (value.contains(COMMENT)) {
                value = value.split(COMMENT)[0];
            }
            map.put(index, value.trim());
        }
        return map;
    }

    /**
     * Split line by delimiter
     * @param line from csv file
     * @return Array fields
     */
    private static String[] splitLine(String line) {
        return line.split(DELIMITER);
    }
}
