package com.github.imgabreuw.backend.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa os dados de métricas educacionais (aprovação, reprovação e abandono) para um nível específico.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EducationMetrics {

    /**
     * Taxa de aprovação dos alunos (em %).
     */
    private double approvalRate;

    /**
     * Taxa de reprovação dos alunos (em %).
     */
    private double failureRate;

    /**
     * Taxa de abandono dos alunos (em %).
     */
    private double dropoutRate;

}
