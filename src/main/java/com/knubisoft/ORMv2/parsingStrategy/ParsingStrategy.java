package com.knubisoft.ORMv2.parsingStrategy;

import com.knubisoft.ORMv2.Table;
import com.knubisoft.ORMv2.sourceInterf.DataReadWriteSource;

public interface ParsingStrategy<T extends DataReadWriteSource> {
    Table parseToTable(T content);
}