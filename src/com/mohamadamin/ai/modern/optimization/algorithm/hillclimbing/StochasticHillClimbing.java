package com.mohamadamin.ai.optimization.algorithm.hillclimbing;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;

import java.util.ArrayList;
import java.util.List;

import static com.mohamadamin.ai.util.IntegerUtils.randomInt;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class StochasticHillClimbing extends GreedyHillClimbing {

    @Override
    protected OptimizationAction chooseNextAction(OptimizationAction lastAction,
                                                  List<OptimizationAction> possibleActions) {
        List<Integer> goodActionIndexed = new ArrayList<>();
        for (int i = 0; i < possibleActions.size(); i++) {
            if (getCompetency(possibleActions.get(i)) > getCompetency(lastAction)) {
                goodActionIndexed.add(i);
            }
        }
        if (goodActionIndexed.size() == 0) {
            return lastAction;
        }
        int randomInt = randomInt(goodActionIndexed.size() + 1);
        return randomInt == goodActionIndexed.size() ?
                lastAction :
                possibleActions.get(goodActionIndexed.get(randomInt));
    }

    @Override
    protected String getName() {
        return "Stochastic Hill Climbing";
    }

}
