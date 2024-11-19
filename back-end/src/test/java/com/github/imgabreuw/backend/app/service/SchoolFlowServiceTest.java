package com.github.imgabreuw.backend.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}