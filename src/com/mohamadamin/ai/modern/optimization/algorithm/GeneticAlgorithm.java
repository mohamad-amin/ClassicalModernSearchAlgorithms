package com.mohamadamin.ai.optimization.algorithm;

import com.mohamadamin.ai.optimization.base.algorithm.OptimizationAlgorithm;
import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationInitialAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToLongFunction;

import static com.mohamadamin.ai.util.IntegerUtils.randomInt;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class GeneticAlgorithm extends OptimizationAlgorithm {

    private final int populationSize;
    private final double mutationRatio;
    private long competencyChecksCount;

    public GeneticAlgorithm(int populationSize, double mutationRatio, long competencyChecksCount) {
        this.populationSize = populationSize;
        this.mutationRatio = mutationRatio;
        this.competencyChecksCount = competencyChecksCount;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<OptimizationState, Long> findBestPossibleCompetency(OptimizationProblem optimizationProblem) {

        this.problem = optimizationProblem;

        // Initial population
        List<OptimizationAction> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(OptimizationInitialAction.from(problem.getNewInitialState()));
        }

        while (true) {

            generationsCount++;

            List<OptimizationAction> children = new ArrayList<>();

            // Parent Selection, Crossover and Offspring
            initialPopulation.sort(Comparator.comparingLong(action -> -problem.getCompetency(action.getDestinationState())));
            for (int i = 0; i < populationSize; i++) {
                int randomMother = randomInt(populationSize/2);
                OptimizationAction mother = initialPopulation.get(randomMother);
                int randomFather;
                while ((randomFather = randomInt(populationSize/2)) == randomMother);
                OptimizationAction father = initialPopulation.get(randomFather);
                children.add(problem.crossover(mother, father));
            }

            // Mutation
            List<Integer> mutationIndexes = new ArrayList<>();
            for (int i = 0; i < populationSize / 4; i++) {
                int randomMutatedOne;
                while (mutationIndexes.contains(randomMutatedOne = randomInt(populationSize)));
                mutationIndexes.add(randomMutatedOne);
            }
            for (Integer mutatedOne : mutationIndexes) {
                children.set(mutatedOne, problem.mutate(children.get(mutatedOne), mutationRatio));
            }

            // Population Selection
            initialPopulation.addAll(children);
            initialPopulation.sort(Comparator.comparingLong(action -> -problem.getCompetency(action.getDestinationState())));
            ToLongFunction<OptimizationAction> mapper = action -> problem.getCompetency(action.getDestinationState());
            double average = initialPopulation.stream().mapToLong(mapper).average().getAsDouble();
            long min = initialPopulation.stream().mapToLong(mapper).min().getAsLong();
            long max = initialPopulation.stream().mapToLong(mapper).max().getAsLong();
            bestInGenerations.add(max);
            worstInGenerations.add(min);
            mediumInGenerations.add(average);
            initialPopulation = initialPopulation.subList(0, populationSize-1);

            competencyChecksCount -= 2 * populationSize;

            if (competencyChecksCount < populationSize) {
                OptimizationState bestFoundState = initialPopulation.get(0).getDestinationState();
                return new Pair<>(bestFoundState, problem.getCompetency(bestFoundState));
            }

        }

    }

    @Override
    protected String getName() {
        return "Genetic";
    }

}
