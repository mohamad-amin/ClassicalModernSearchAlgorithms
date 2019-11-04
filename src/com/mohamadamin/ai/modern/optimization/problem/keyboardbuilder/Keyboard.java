package com.mohamadamin.ai.optimization.problem.keyboardbuilder;

import com.mohamadamin.ai.optimization.base.problem.OptimizationState;

import java.util.List;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class Keyboard extends OptimizationState {

    private final String left;
    private final String right;

    public Keyboard(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "{ " +
                "left=" + left +
                ", right=" + right +
                " }";
    }

}
