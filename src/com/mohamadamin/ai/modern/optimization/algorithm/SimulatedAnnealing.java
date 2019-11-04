package com.mohamadamin.ai.optimization.algorithm;

import com.mohamadamin.ai.optimization.base.algorithm.OptimizationAlgorithm;
import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationInitialAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import javafx.util.Pair;

import java.util.Collections;
import java.util.List;

import static com.mohamadamin.ai.util.IntegerUtils.getCompetencyRatio;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class SimulatedAnnealing extends OptimizationAlgorithm {


    private double temperature;
    private long minCompetency;
    private long maxCompetency;
    private final double decreaseRatio;

    public SimulatedAnnealing(double temperature, double decreaseRatio) {
        this.temperature = temperature;
        this.decreaseRatio = decreaseRatio;
    }

    @SuppressWarnings("unchecked")
    protected long getCompetency(OptimizationAction action) {
        return problem.getCompetency(action.getDestinationState());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<OptimizationState, Long> findBestPossibleCompetency(OptimizationProblem optimizationProblem) {

        this.problem = optimizationProblem;
        this.minCompetency = problem.getMinCompetency();
        this.maxCompetency = problem.getMaxCompetency();

        OptimizationAction action = OptimizationInitialAction.from(optimizationProblem.getNewInitialState());
//        System.out.println(Environment.DIVIDER);

        boolean changed;
        do {
            changed = false;
            long competency = getCompetency(action);
            List<OptimizationAction> actions = problem.getActions(action.getDestinationState());
            Collections.shuffle(actions);
            expandedNodes++;
            for (Object newActionObject : actions) {
                seenNodes++;
                OptimizationAction newAction = (OptimizationAction) newActionObject;
                if (newAction.getDestinationState().equals(action.getSourceState())) {
                    continue;
                }
                double ratio = getCompetencyRatio(getCompetency(newAction), competency, minCompetency, maxCompetency);
                if (temperature > ratio) {
//                    System.out.println(String.format("%s (%d) -> %s (%d)", action, competency, newAction, getCompetency(newAction)));
//                    System.out.println(String.format("Ratio -> %.2f, temp -> %.2f", ratio, temperature));
                    action =  newAction;
                    changed = true;
                }
                temperature = Math.max(0d, temperature - decreaseRatio);
                if (changed) {
                    break;
                }
            }
        } while (changed);

        OptimizationState bestFoundState = action.getDestinationState();
        return new Pair<>(bestFoundState, problem.getCompetency(bestFoundState));

    }

    @Override
    protected String getName() {
        return "Simulated Annealing";
    }

}
