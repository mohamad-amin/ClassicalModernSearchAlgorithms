package com.mohamadamin.ai.optimization.problem.graphcoloring;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.optimization.problem.graphcoloring.GraphAction;
import com.mohamadamin.ai.optimization.problem.graphcoloring.GraphState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.mohamadamin.ai.util.IntegerUtils.randomInt;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class GraphColoring implements OptimizationProblem<GraphState, GraphAction> {

    private int edgesCount;
    private int nodesCount;
    private int colorsCount;
    private boolean connectionGraph[][];

    @Override
    public GraphState getNewInitialState() {
        int[] colors = new int[nodesCount];
        for (int i = 0; i < nodesCount; i++) {
            colors[i] = randomInt(colorsCount);
        }
        return new GraphState(colors);
    }

    @Override
    public void retrieveInput() {
        Scanner in = new Scanner(System.in);
        colorsCount = in.nextInt();
        nodesCount = in.nextInt();
        edgesCount = in.nextInt();
        connectionGraph = new boolean[nodesCount][nodesCount];
        for (int i = 0; i < edgesCount; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            connectionGraph[x][y] = true;
            connectionGraph[y][x] = true;
        }
    }

    @Override
    public List<GraphAction> getActions(GraphState state) {
        List<GraphAction> actions = new ArrayList<>();
        for (int i = 0; i < nodesCount; i++) {
            for (int j = 0; j < colorsCount; j++) {
                if (state.getColors()[i] != j) {
                    int[] colors = Arrays.copyOf(state.getColors(), nodesCount);
                    colors[i] = j;
                    actions.add(new GraphAction(state, new GraphState(colors)));
                }
            }
        }
        return actions;
    }

    @Override
    public long getCompetency(GraphState state) {
        int[] colors = state.getColors();
        int edgesWithDifferentColorCount = 0;
        for (int i = 0; i < nodesCount; i++) {
            for (int j = 0; j < nodesCount; j++) {
                if (connectionGraph[i][j] && colors[i] != colors[j]) {
                    edgesWithDifferentColorCount++;
                }
            }
        }
        return edgesWithDifferentColorCount / 2;
    }

    @Override
    public long getMinCompetency() {
        return 0;
    }

    @Override
    public long getMaxCompetency() {
        return edgesCount;
    }

    @Override
    public GraphAction crossover(OptimizationAction<GraphState> mother, OptimizationAction<GraphState> father) {
        return null;
    }

    @Override
    public GraphAction mutate(OptimizationAction<GraphState> newbie, double mutationRatio) {
        return null;
    }

}
