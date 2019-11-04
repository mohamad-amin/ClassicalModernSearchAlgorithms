package com.mohamadamin.ai.optimization.base.algorithm;

import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public abstract class OptimizationAlgorithm {

    protected long generationsCount;
    protected OptimizationProblem problem;
    protected long seenNodes, expandedNodes;
    protected final List<Long> bestInGenerations = new ArrayList<>();
    protected final List<Long> worstInGenerations = new ArrayList<>();
    protected final List<Double> mediumInGenerations = new ArrayList<>();

    public abstract Pair<OptimizationState, Long> findBestPossibleCompetency(OptimizationProblem optimizationProblem);

    public long getSeenNodes() {
        return seenNodes;
    }

    public long getExpandedNodes() {
        return expandedNodes;
    }

    public long getGenerationsCount() {
        return generationsCount;
    }

    public List<Long> getBestInGenerations() {
        return bestInGenerations;
    }

    public List<Long> getWorstInGenerations() {
        return worstInGenerations;
    }

    public List<Double> getMediumInGenerations() {
        return mediumInGenerations;
    }

    protected abstract String getName();

    @Override
    public String toString() {
        return "{ " +
                "name=" + getName() +
                " }";
    }

}
