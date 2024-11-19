package com.github.imgabreuw.backend.app.service;

import com.github.imgabreuw.backend.data.model.SchoolFlow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SchoolFlowServiceTest {

    @Autowired
    private SchoolFlowService underTest;

    @BeforeEach
    void setUp() {
        underTest.loadFromCSV(
                "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2023 - por escola.csv",
                "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2022 - por escola.csv",
                "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2021 - por escola.csv"
        );
    }

    @Test
    void totalOfSchools() {
    }

    @Test
    void averageOfDropoutRateHighSchool() {
        double averagedOfDropoutRate = underTest.averageOfDropoutRateHighSchool();

        assertThat(averagedOfDropoutRate).isNotNegative();
        assertThat(averagedOfDropoutRate).isNotZero();
    }

    @Test
    void loadFromCSV() {
    }

    @Test
    void findAll() {
        List<SchoolFlow> schoolFlows = underTest.findAll();

        assertThat(schoolFlows).isNotNull();
        assertThat(schoolFlows).isNotEmpty();
    }

    @Test
    void deleteAll() {
        underTest.deleteAll();

        assertThat(underTest.findAll()).isEmpty();
    }
}