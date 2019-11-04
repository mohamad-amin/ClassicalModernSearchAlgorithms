package com.mohamadamin.ai.optimization.algorithm.hillclimbing;

import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import javafx.util.Pair;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class RandomRestartHillClimbing extends GreedyHillClimbing {

    @Override
    public Pair<OptimizationState, Long> findBestPossibleCompetency(OptimizationProblem optimizationProblem) {

        Pair<OptimizationState, Long> result = super.findBestPossibleCompetency(optimizationProblem);
        Pair<OptimizationState, Long> test = super.findBestPossibleCompetency(optimizationProblem);

        while (test.getValue().longValue() != result.getValue().longValue()) {
            if (test.getValue() > result.getValue()) {
                result = test;
            }
            test = super.findBestPossibleCompetency(optimizationProblem);
            if (test == null) {
                break;
            }
        }

        return result;

    }

    @Override
    protected String getName() {
        return "Random Restart Hill Climbing";
    }

}
