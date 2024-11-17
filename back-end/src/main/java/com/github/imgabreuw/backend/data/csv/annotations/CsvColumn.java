package com.github.imgabreuw.backend.data.csv.annotations;

import java.lang.annotation.*;

/**
 * Anotação para mapear um campo de uma classe ao nome de uma coluna no arquivo CSV.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface CsvColumn {

    /**
     * Nome da coluna no arquivo CSV.
     *
     * @return o nome da coluna.
     */
    String value();

    /**
     * Nome do campo dentro da classe composta (caso aplicável).
     *
     * @return o nome do subcampo ou vazio se o mapeamento for direto.
     */
    String fieldName() default "";

}


