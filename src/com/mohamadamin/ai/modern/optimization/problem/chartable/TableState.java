package com.mohamadamin.ai.optimization.problem.chartable;

import com.mohamadamin.ai.optimization.base.problem.OptimizationState;
import com.mohamadamin.ai.util.IntegerUtils;

import java.util.Arrays;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class TableState extends OptimizationState {

    private char[][] table;

    public TableState(char[][] table) {
        this.table = table;
    }

    public char[][] getTable() {
        return table;
    }

    @Override
    public String toString() {
        return "{ " +
                "table=" + IntegerUtils.twoDArrayToString(table, table.length) +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableState)) return false;
        TableState that = (TableState) o;
        return Arrays.deepEquals(table, that.table);
    }

}
