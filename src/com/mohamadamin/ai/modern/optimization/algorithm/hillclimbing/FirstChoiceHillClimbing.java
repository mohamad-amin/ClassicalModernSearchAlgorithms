package com.mohamadamin.ai.optimization.algorithm.hillclimbing;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;

import java.util.List;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class FirstChoiceHillClimbing extends GreedyHillClimbing {

    @Override
    protected OptimizationAction chooseNextAction(OptimizationAction lastAction,
                                                  List<OptimizationAction> possibleActions) {
        for (OptimizationAction possibleAction : possibleActions) {
            if (getCompetency(possibleAction) > getCompetency(lastAction)) {
                return possibleAction;
            }
        }
        return lastAction;
    }

    @Override
    protected String getName() {
        return "First-Choice Hill Climbing";
    }

}
