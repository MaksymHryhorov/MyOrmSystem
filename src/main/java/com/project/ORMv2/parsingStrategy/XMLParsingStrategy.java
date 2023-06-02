package com.project.ORMv2.parsingStrategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.project.ORMv2.mapper.Table;
import com.project.rwsouce.FileReadWriteSource;
import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMLParsingStrategy implements ParsingStrategy<FileReadWriteSource> {
    @SneakyThrows
    @Override
    public Table parseToTable(FileReadWriteSource content) {
        XmlMapper mapper = new XmlMapper();
        JsonNode tree = mapper.readTree(content.getContent());
        Map<Integer, Map<String, String>> result = buildTable(tree);
        return new Table(result);
    }

    /**
     * Filled map with values from xml file
     * @param tree array xml node
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
     * Put each row from xml file to a map
     * @param each row in xml file
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
