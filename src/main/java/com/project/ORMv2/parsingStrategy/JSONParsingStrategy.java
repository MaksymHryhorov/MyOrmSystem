package com.project.ORMv2.parsingStrategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.project.rwsouce.FileReadWriteSource;
import com.project.ORMv2.mapper.Table;
import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONParsingStrategy implements ParsingStrategy<FileReadWriteSource> {

    /**
     * Build new Table
     *
     * @param content file
     * @return Table - Map(key, value)
     */
    @SneakyThrows
    @Override
    public Table parseToTable(FileReadWriteSource content) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(content.getContent());
        Map<Integer, Map<String, String>> result = buildTable(tree);
        return new Table(result);
    }

    /**
     * Filled map with values from json file
     *
     * @param tree array json node
     * @return Map (key, Map(key, value))
     */
    private Map<Integer, Map<String, String>> buildTable(JsonNode tree) {
        Map<Integer, Map<String, String>> map = new LinkedHashMap<>();
        int index = 0;
        for (JsonNode each : tree) {
            Map<String, String> item = buildRow(each);
            map.put(index, item);
            index++;
        }
        return map;
    }

    /**
     * Put each row from json file to a map
     *
     * @param each row in json file
     * @return Map (key, value)
     */
    private Map<String, String> buildRow(JsonNode each) {
        Map<String, String> item = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> itr = each.fields();
        while (itr.hasNext()) {
            Map.Entry<String, JsonNode> next = itr.next();
            if (next.getValue().getClass().equals(DoubleNode.class) ||
                    next.getValue().getClass().equals(IntNode.class)) {
                item.put(next.getKey(), String.valueOf(next.getValue()));
            } else {
                item.put(next.getKey(), next.getValue().textValue());
            }

        }
        return item;
    }
}