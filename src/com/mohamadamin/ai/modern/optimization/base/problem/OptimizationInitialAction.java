package com.mohamadamin.ai.optimization.base.problem;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public class OptimizationInitialAction<S extends OptimizationState> extends OptimizationAction<S> {

    public static <S extends OptimizationState> OptimizationInitialAction<S> from(S state) {
        return new OptimizationInitialAction<>(state);
    }

    private OptimizationInitialAction(S state) {
        super(null, state);
    }

}
