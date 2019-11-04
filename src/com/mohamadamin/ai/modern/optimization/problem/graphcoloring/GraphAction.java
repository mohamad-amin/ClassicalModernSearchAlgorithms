package com.mohamadamin.ai.optimization.problem.graphcoloring;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class GraphAction extends OptimizationAction<GraphState> {

    public GraphAction(GraphState sourceState, GraphState destinationState) {
        super(sourceState, destinationState);
    }

}
