package com.mohamadamin.ai.optimization.algorithm.hillclimbing;

import com.mohamadamin.ai.optimization.base.algorithm.OptimizationAlgorithm;
import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationInitialAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class GreedyHillClimbing extends OptimizationAlgorithm {

    protected OptimizationAction getInitialAction() {
        return OptimizationInitialAction.from(problem.getNewInitialState());
    }

    @SuppressWarnings("unchecked")
    protected long getCompetency(OptimizationAction action) {
        return problem.getCompetency(action.getDestinationState());
    }

    @SuppressWarnings("unchecked")
    protected OptimizationAction chooseNextAction(OptimizationAction lastAction,
                                                  List<OptimizationAction> possibleActions) {
        OptimizationAction bestAction = lastAction;
        for (int i = 0; i < possibleActions.size(); i++) {
            if (getCompetency(possibleActions.get(i)) > getCompetency(bestAction)) {
                bestAction = possibleActions.get(i);
            }
        }
        seenNodes += possibleActions.size();
        return bestAction;
    }

    protected boolean onSummit(long competency, List<OptimizationAction> actions) {
        for (OptimizationAction action : actions) {
            if (getCompetency(action) > competency) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<OptimizationState, Long> findBestPossibleCompetency(OptimizationProblem optimizationProblem) {

        this.problem = optimizationProblem;

        OptimizationAction action;
        OptimizationAction nextAction = getInitialAction();
        List<OptimizationAction> possibleActions;
        do {
            action = nextAction;
            expandedNodes++;
            possibleActions = problem.getActions(action.getDestinationState());
            nextAction = chooseNextAction(action, possibleActions);
        } while (!onSummit(getCompetency(action), possibleActions));

        OptimizationState bestFoundState = action.getDestinationState();
        return new Pair<>(bestFoundState, problem.getCompetency(bestFoundState));

    }

    @Override
    protected String getName() {
        return "Hill Climbing";
    }

}
