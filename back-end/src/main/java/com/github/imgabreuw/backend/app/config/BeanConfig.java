package com.github.imgabreuw.backend.app.config;

import com.github.imgabreuw.backend.data.model.SchoolFlow;
import com.github.imgabreuw.backend.database.repository.AVLRepositoryImpl;
import com.github.imgabreuw.backend.database.repository.BSTRepositoryImpl;
import com.github.imgabreuw.backend.database.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    @Qualifier("avlSchoolFlowTreeRepository")
    public TreeRepository<SchoolFlow> avlSchoolFlowTreeRepository() {
        return new AVLRepositoryImpl<>();
    }

    @Bean
    @Qualifier("bstSchoolFlowTreeRepository")
    public TreeRepository<SchoolFlow> bstSchoolFlowTreeRepository() {
        return new BSTRepositoryImpl<>();
    }

}
