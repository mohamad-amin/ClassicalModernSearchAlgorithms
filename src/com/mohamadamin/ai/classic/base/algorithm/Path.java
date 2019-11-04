package com.mohamadamin.ai.classic.base.algorithm;

import com.mohamadamin.ai.classic.base.problem.Action;

import java.util.List;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class Path {

    private final long cost;
    private final List<Action> actions;

    public Path(long cost, List<Action> actions) {
        this.cost = cost;
        this.actions = actions;
    }

    public long getCost() {
        return cost;
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "Path{" +
                "cost=" + cost +
                ", actions=" + actions +
                '}';
    }

}
