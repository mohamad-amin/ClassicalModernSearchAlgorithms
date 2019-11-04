package com.mohamadamin.ai.optimization.problem.graphcoloring;

import com.mohamadamin.ai.optimization.base.problem.OptimizationState;

import java.util.Arrays;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class GraphState extends OptimizationState {

    private final int[] colors;

    public GraphState(int[] colors) {
        this.colors = colors;
    }

    public int[] getColors() {
        return colors;
    }

    @Override
    public String toString() {
        return "{ " +
                "colors=" + Arrays.toString(colors) +
                " }";
    }

}
