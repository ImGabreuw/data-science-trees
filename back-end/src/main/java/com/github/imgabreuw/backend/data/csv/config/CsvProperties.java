package com.github.imgabreuw.backend.data.csv.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "csv")
@Validated
@Data
public class CsvProperties {

    @NotBlank
    private String delimiter;

    @Min(1)
    private int batchSize;

    @NotNull
    private Duration cacheExpiration;

}
