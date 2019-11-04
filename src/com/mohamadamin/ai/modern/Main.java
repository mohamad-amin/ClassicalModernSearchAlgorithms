package com.mohamadamin.ai;

import com.mohamadamin.ai.optimization.algorithm.GeneticAlgorithm;
import com.mohamadamin.ai.optimization.base.algorithm.OptimizationAlgorithm;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import com.mohamadamin.ai.optimization.problem.keyboardbuilder.KeyboardBuilding;
import com.mohamadamin.ai.util.Environment;
import javafx.util.Pair;

public class Main {


    public static void main(String[] args) {
        OptimizationAlgorithm algorithm = new GeneticAlgorithm(10, 1d, 500);
        OptimizationProblem problem = new KeyboardBuilding();
        runAlgorithmOnProblem(algorithm, problem, false);
    }

    private static void runAlgorithmOnProblem(OptimizationAlgorithm algorithm,
                                              OptimizationProblem problem, boolean printNodeStatistics) {
        problem.retrieveInput();
        Pair<OptimizationState, Long> bestFoundState = algorithm.findBestPossibleCompetency(problem);
        if (bestFoundState == null) {
            System.out.println("Couldn't find the answer :(");
        } else {
            System.out.println(String.format("%sRun statistics:", Environment.DIVIDER));
            if (printNodeStatistics) {
                System.out.println(String.format("Seen Nodes -> %s", algorithm.getSeenNodes()));
                System.out.println(String.format("Expanded Nodes -> %s", algorithm.getExpandedNodes()));
            } else {
                System.out.println(String.format("Generations count: %d, stats for each generation:", algorithm.getGenerationsCount()));
                for (int i = 0; i < algorithm.getGenerationsCount(); i++) {
                    System.out.println(String.format("Worst in generation %d -> %d", i, algorithm.getBestInGenerations().get(i)));
                    System.out.println(String.format("Average in generation %d -> %.2f", i, algorithm.getMediumInGenerations().get(i)));
                    System.out.println(String.format("Best in generation %d -> %d", i, algorithm.getBestInGenerations().get(i)));
                }
            }
            System.out.println(String.format(
                    "%sBest state found with competency of %d: %s",
                    Environment.DIVIDER, bestFoundState.getValue(), bestFoundState.getKey()
            ));
            double successRatio = ((double) (bestFoundState.getValue() - problem.getMinCompetency())
                    / (problem.getMaxCompetency() - problem.getMinCompetency())) * 100d;
            System.out.println(String.format(
                    "Max competency: %d, Min competency: %d, Success ration: %.2f",
                    problem.getMaxCompetency(),
                    problem.getMinCompetency(),
                    successRatio
            ));
        }
    }

}
/* Graph Coloring
2
4
4
1 2
2 3
3 4
4 1
 */
/* Char Table
3
4
a p t
m l b
k l o
u o c
4
cool
cat
talk
go
 */