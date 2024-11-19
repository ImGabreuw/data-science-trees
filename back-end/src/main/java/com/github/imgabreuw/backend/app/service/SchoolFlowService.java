package com.github.imgabreuw.backend.app.service;

import com.github.imgabreuw.backend.data.csv.CsvReader;
import com.github.imgabreuw.backend.data.model.SchoolFlow;
import com.github.imgabreuw.backend.database.repository.TreeRepository;
import com.github.imgabreuw.backend.metrics.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class SchoolFlowService {

    private final TreeRepository<SchoolFlow> repository;
    private final CsvReader csvReader;

    @Timed
    public void loadFromCSV(String filePath) {
        List<SchoolFlow> schoolFlows = csvReader.readCsv(filePath, SchoolFlow.class);
        repository.saveAll(schoolFlows);
    }

    // FIXME ajustar métodos para a API

    public void findByIdPrimaryKey() {
        Predicate<SchoolFlow> byPrimaryKey = item ->
                item.getYear() == 0 &&
                        item.getSchoolId() == 21118 &&
                        item.getEducationBoardId() == 20419;

        repository
                .find(byPrimaryKey)
                .ifPresent(System.out::println);
    }

}
