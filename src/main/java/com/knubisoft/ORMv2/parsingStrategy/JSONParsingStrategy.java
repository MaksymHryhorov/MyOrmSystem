package com.knubisoft.ORMv2.parsingStrategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knubisoft.ORMv2.FileReadWriteSource;
import com.knubisoft.ORMv2.Table;
import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONParsingStrategy implements ParsingStrategy<FileReadWriteSource> {

    /**
     * Build new Table
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
     * @param each row in json file
     * @return Map (key, value)
     */
    private Map<String, String> buildRow(JsonNode each) {
        Map<String, String> item = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> itr = each.fields();
        while (itr.hasNext()) {
            Map.Entry<String, JsonNode> next = itr.next();
            item.put(next.getKey(), next.getValue().textValue());
        }
        return item;
    }
}