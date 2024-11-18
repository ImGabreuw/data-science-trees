package com.github.imgabreuw.backend.app.config;

import com.github.imgabreuw.backend.database.structure.factory.BSTStrategy;
import com.github.imgabreuw.backend.database.structure.factory.TreeStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public TreeStrategy treeStrategy() {
        return new BSTStrategy();
    }

}
