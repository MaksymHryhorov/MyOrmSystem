package com.project.ORMv2.parsingStrategy;

import com.project.ORMv2.mapper.Table;
import com.project.ORMv2.sourceInterf.DataReadWriteSource;

public interface ParsingStrategy<T extends DataReadWriteSource> {
    Table parseToTable(T content);
}