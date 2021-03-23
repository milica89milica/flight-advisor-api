package com.htec.fa_api.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvReader {

    private static final CsvMapper mapper;

    static {
        mapper = (CsvMapper) new CsvMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN,true)
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    }

    public static <T> List<T> read(Class<T> type, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(type).withoutHeader(); //dont read first line as a header!
        ObjectReader reader = mapper.readerFor(type).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
}
