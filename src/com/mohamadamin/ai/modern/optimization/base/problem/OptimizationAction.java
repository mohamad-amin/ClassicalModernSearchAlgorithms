package com.mohamadamin.ai.optimization.base.problem;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public abstract class OptimizationAction<S extends OptimizationState> {

    private final S sourceState;
    private final S destinationState;

    public OptimizationAction(S sourceState, S destinationState) {
        this.sourceState = sourceState;
        this.destinationState = destinationState;
    }

    public S getSourceState() {
        return sourceState;
    }

    public S getDestinationState() {
        return destinationState;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OptimizationAction && destinationState.equals(((OptimizationAction) obj).getDestinationState());
    }

    @Override
    public String toString() {
        return "{ " +
                "source=" + sourceState +
                ", dest=" + destinationState +
                " }";
    }

}
