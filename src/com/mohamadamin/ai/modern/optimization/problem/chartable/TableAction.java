package com.mohamadamin.ai.optimization.problem.chartable;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class TableAction extends OptimizationAction<TableState> {

    public TableAction(TableState sourceState, TableState destinationState) {
        super(sourceState, destinationState);
    }

}
