package com.mohamadamin.ai.optimization.base.problem;

/**
 * Created by MohamadAmin on 6/8/18.
 * Todo: don't check tag on comparing
 */
public abstract class OptimizationState {

    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "{ " +
                "tag=" + tag +
                " }";
    }

}
