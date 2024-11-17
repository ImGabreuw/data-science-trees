package com.github.imgabreuw.backend.data.model;

import com.github.imgabreuw.backend.data.csv.annotations.CsvColumn;
import com.github.imgabreuw.backend.data.csv.annotations.CsvColumnMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa os dados escolares relacionados a aprovação, reprovação e abandono por níveis de ensino.
 * <p>
 * Esta classe organiza as informações de forma estruturada, permitindo extensibilidade e legibilidade.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SchoolFlow implements Comparable<SchoolFlow> {

    /**
     * Ano para o qual os dados foram coletados.
     */
    @CsvColumn("ANO_LETIVO")
    private int year;

    /**
     * Código da escola.
     */
    @CsvColumn("CD_ESCOLA")
    private int schoolId;

    /**
     * Código da diretoria de ensino.
     */
    @CsvColumn("CD_DIRETORIA")
    private int educationBoardId;

    /**
     * Nome da diretoria de ensino.
     */
    @CsvColumn("NM_DIRETORIA")
    private String educationBoardName;

    /**
     * Nome do município.
     */
    @CsvColumn("NM_MUNICIPIO")
    private String municipalityName;

    /**
     * Código da rede de ensino (1: Estadual).
     */
    @CsvColumn("CD_REDE_ENSINO")
    private int educationNetworkId;

    /**
     * Nome completo da escola
     */
    @CsvColumn("NM_COMPLETO_ESCOLA")
    private String schoolName;

    /**
     * Código do tipo da escola.
     */
    @CsvColumn("CD_TP_IDENTIFICADOR")
    private int schoolTypeId;

    /**
     * Dados de aprovação, reprovação e abandono do Ensino Fundamental 1.
     */
    @CsvColumnMapping({
            @CsvColumn(value = "APR_1", fieldName = "approvalRate"),
            @CsvColumn(value = "REP_1", fieldName = "failureRate"),
            @CsvColumn(value = "ABA_1", fieldName = "dropoutRate")
    })
    private EducationMetrics fundamentalInitial;

    /**
     * Dados de aprovação, reprovação e abandono do Ensino Fundamental 2.
     */
    @CsvColumnMapping({
            @CsvColumn(value = "APR_2", fieldName = "approvalRate"),
            @CsvColumn(value = "REP_2", fieldName = "failureRate"),
            @CsvColumn(value = "ABA_2", fieldName = "dropoutRate")
    })
    private EducationMetrics fundamentalFinal;

    /**
     * Dados de aprovação, reprovação e abandono do Ensino Médio.
     */
    @CsvColumnMapping({
            @CsvColumn(value = "APR_3", fieldName = "approvalRate"),
            @CsvColumn(value = "REP_3", fieldName = "failureRate"),
            @CsvColumn(value = "ABA_3", fieldName = "dropoutRate")
    })
    private EducationMetrics highSchool;

    /**
     * Compara este objeto SchoolFlow com outro para ordenação.
     * A comparação é baseada nos campos considerados chave primária ({@code year}, {@code schoolId}, {@code educationBoardId}).
     *
     * @param other o objeto a ser comparado.
     * @return um valor negativo, zero ou positivo conforme este objeto seja menor, igual ou maior que o objeto comparado.
     */
    @Override
    public int compareTo(SchoolFlow other) {
        int yearComparison = Integer.compare(this.year, other.year);
        if (yearComparison != 0) {
            return yearComparison;
        }

        int schoolComparison = Integer.compare(this.schoolId, other.schoolId);
        if (schoolComparison != 0) {
            return schoolComparison;
        }

        return Integer.compare(this.educationBoardId, other.educationBoardId);
    }

}
