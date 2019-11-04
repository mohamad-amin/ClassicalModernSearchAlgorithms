package com.mohamadamin.ai.optimization.problem.keyboardbuilder;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class ButtonTransition extends OptimizationAction<Keyboard> {

    public ButtonTransition(Keyboard sourceState, Keyboard destinationState) {
        super(sourceState, destinationState);
    }

}
