package com.github.imgabreuw.backend.app.service;

import com.github.imgabreuw.backend.data.csv.CsvReader;
import com.github.imgabreuw.backend.data.model.SchoolFlow;
import com.github.imgabreuw.backend.database.repository.TreeRepository;
import com.github.imgabreuw.backend.metrics.Timed;
import com.github.imgabreuw.backend.utils.StreamHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SchoolFlowService {

    private final TreeRepository<SchoolFlow> repository;
    private final CsvReader csvReader;

    public SchoolFlowService(@Qualifier("bstSchoolFlowTreeRepository") TreeRepository<SchoolFlow> repository, CsvReader csvReader) {
        this.repository = repository;
        this.csvReader = csvReader;
    }

    @Timed
    public void loadFromCSV(String... filesPath) {
        Arrays.stream(filesPath)
                .map(filePath -> csvReader.readCsv(filePath, SchoolFlow.class))
                .forEach(repository::saveAll);
    }

    public long totalOfSchools() {
        return repository
                .findAll()
                .filter(StreamHelper.distinctByKey(SchoolFlow::getSchoolName))
                .count();
    }

    public double averageOfDropoutRateHighSchool() {
        return repository
                .findAll()
                .filter(StreamHelper.distinctByKey(SchoolFlow::getSchoolName))
                .mapToDouble(value -> value.getHighSchool().getDropoutRate())
                .average()
                .orElse(0.0);
    }

}
