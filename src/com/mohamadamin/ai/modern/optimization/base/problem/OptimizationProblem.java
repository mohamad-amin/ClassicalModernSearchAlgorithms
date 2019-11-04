package com.mohamadamin.ai.optimization.base.problem;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public interface OptimizationProblem<S extends OptimizationState, A extends OptimizationAction> {

    @NotNull
    S getNewInitialState();

    void retrieveInput();

    @NotNull
    List<A> getActions(S state);

    long getCompetency(S state);

    long getMinCompetency();

    long getMaxCompetency();

    A crossover(OptimizationAction<S> mother, OptimizationAction<S> father);

    A mutate(OptimizationAction<S> newbie, double mutationRatio);

}
