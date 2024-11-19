package com.github.imgabreuw.backend.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchoolFlowServiceTest {

    @Autowired
    private SchoolFlowService underTest;

    @Test
    void loadFromCSV() {
        underTest.loadFromCSV(
                "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2023 - por escola.csv",
                "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2022 - por escola.csv",
                "/home/geron/Projects/data-science-trees/back-end/dataset/Fluxo Escolar 2021 - por escola.csv"
        );
    }

}