package com.github.imgabreuw.backend.data.csv;

import com.github.imgabreuw.backend.data.model.SchoolFlow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CsvReaderTest {

    @Autowired
    private CsvReader underTest;

    @Test
    void shouldReadCSV() {
        String filePath = "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2023 - por escola.csv";

        List<SchoolFlow> schoolFlows = underTest.readCsv(filePath, SchoolFlow.class);

        assertThat(schoolFlows).isNotEmpty();
        assertThat(schoolFlows).allMatch(Objects::nonNull);
    }

}