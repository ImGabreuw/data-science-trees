package com.github.imgabreuw.backend.data.csv;

import com.github.imgabreuw.backend.data.model.SchoolFlow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;

@SpringBootTest
class CsvReaderTest {

    @Autowired
    private CsvReader csvReader;

    @Test
    void shouldReadCSV() {
        Path filePath = Path.of("/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2023 - por escola.csv");

        List<SchoolFlow> schoolFlows = csvReader.readCsv(filePath, SchoolFlow.class);
        System.out.println(schoolFlows.subList(0, 5));
    }
}