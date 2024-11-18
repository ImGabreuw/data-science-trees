package com.github.imgabreuw.backend.database.repository;

import com.github.imgabreuw.backend.database.structure.factory.TreeStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TreeRepositoryFactory {

    private final Map<String, TreeStrategy> strategies;

    public TreeRepositoryFactory(List<TreeStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getClass().getSimpleName(),
                        strategy -> strategy
                ));
    }

    public <T extends Comparable<T>> TreeRepository<T> createRepository(String strategyName) {
        TreeStrategy strategy = strategies.get(strategyName);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid tree strategy: " + strategyName);
        }

        return new BinaryTreeRepositoryImpl<>(strategy);
    }

}
