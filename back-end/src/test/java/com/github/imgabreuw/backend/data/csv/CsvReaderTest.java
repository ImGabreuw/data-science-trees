package com.github.imgabreuw.backend.data.csv;

import com.github.imgabreuw.backend.data.model.SchoolFlow;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class CsvReaderTest {

    @Test
    void shouldReadCSV() {
        Path filePath = Path.of("/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2023 - por escola.csv");

        try {
            List<SchoolFlow> schoolFlows = CsvReader.readCsv(filePath, SchoolFlow.class);
            System.out.println(schoolFlows.subList(0, 5));
        } catch (IOException | ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}