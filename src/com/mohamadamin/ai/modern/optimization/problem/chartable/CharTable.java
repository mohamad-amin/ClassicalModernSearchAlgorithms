package com.mohamadamin.ai.optimization.problem.chartable;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class CharTable implements OptimizationProblem<TableState, TableAction> {

    private int width;
    private int height;
    private String[] dictionary;
    private char[][] initialTable;

    @Override
    public TableState getNewInitialState() {
        return new TableState(initialTable);
    }

    @Override
    public void retrieveInput() {
        Scanner in = new Scanner(System.in);
        width = in.nextInt();
        height = in.nextInt();
        initialTable = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                initialTable[i][j] = in.next().charAt(0);
            }
        }
        int dictionaryItemCount = in.nextInt();
        dictionary = new String[dictionaryItemCount];
        for (int i = 0; i < dictionaryItemCount; i++) {
            dictionary[i] = in.next();
        }
    }

    @Override
    public List<TableAction> getActions(TableState state) {
        List<TableAction> actions = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (Pair<Integer, Integer> neighbour : getNeighbours(i, j)) {
                    actions.add(new TableAction(state, new TableState(replace(state.getTable(), i, j, neighbour))));
                }
            }
        }
        return actions;
    }

    private char[][] replace(char[][] table, int i, int j, Pair<Integer, Integer> pair) {
        char[][] newTable = new char[height][width];
        for (int k = 0; k < height; k++) {
            newTable[k] = Arrays.copyOf(table[k], width);
        }
        newTable[i][j] = newTable[pair.getKey()][pair.getValue()];
        newTable[pair.getKey()][pair.getValue()] = table[i][j];
        return newTable;
    }

    private List<Pair<Integer, Integer>> getNeighbours(int y, int x) {
        List<Pair<Integer, Integer>> neighbours = new ArrayList<>();
        if (x != width-1) {
            neighbours.add(new Pair<>(y, x+1));
        }
        if (y != height-1) {
            neighbours.add(new Pair<>(y+1, x));
        }
        return neighbours;
    }

    @Override
    public long getCompetency(TableState state) {
        int wordsCount = 0;
        for (String word : dictionary) {
            if (stateContainsWord(state, word)) {
                wordsCount++;
            }
        }
        return wordsCount;
    }

    @Override
    public long getMinCompetency() {
        return 0;
    }

    @Override
    public long getMaxCompetency() {
        return dictionary.length;
    }

    private boolean stateContainsWord(TableState state, String word) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (state.getTable()[i][j] == word.charAt(0)) {
                    if (wordStartsAt(i, j, word.substring(1), state.getTable())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean wordStartsAt(int y, int x, String word, char table[][]) {

        if (word == null || word.isEmpty()) {
            return true;
        }

        if (x+1 < width && word.charAt(0) == table[y][x+1]) {
            return wordStartsAt(y, x+1, word.substring(1), table);
        }
        if (x-1 >= 0 && word.charAt(0) == table[y][x-1]) {
            return wordStartsAt(y, x-1, word.substring(1), table);
        }
        if (y+1 < height && word.charAt(0) == table[y+1][x]) {
            return wordStartsAt(y+1, x, word.substring(1), table);
        }
        if (y-1 >= 0 && word.charAt(0) == table[y-1][x]) {
            return wordStartsAt(y-1, x, word.substring(1), table);
        }

        return false;

    }

    @Override
    public TableAction crossover(OptimizationAction<TableState> mother, OptimizationAction<TableState> father) {
        return null;
    }

    @Override
    public TableAction mutate(OptimizationAction<TableState> newbie, double mutationRatio) {
        return null;
    }

}
