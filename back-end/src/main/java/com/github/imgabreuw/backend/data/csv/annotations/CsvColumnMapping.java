package com.github.imgabreuw.backend.data.csv.annotations;

import java.lang.annotation.*;

/**
 * Agrupa múltiplas anotações @CsvColumn para mapear uma classe ou campo composto a colunas no CSV.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface CsvColumnMapping {

    /**
     * Lista de mapeamentos de colunas para o CSV.
     *
     * @return as anotações CsvColumn associadas.
     */
    CsvColumn[] value();

}

