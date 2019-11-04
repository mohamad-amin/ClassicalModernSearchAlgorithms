package com.mohamadamin.ai.classic.problem.rubic;

import com.mohamadamin.ai.classic.base.problem.Action;
import com.mohamadamin.ai.classic.base.problem.State;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class RubicAction extends Action<State> {

    public RubicAction(String name, State sourceState, State destinationState) {
        super(1, name, sourceState, destinationState);
    }

    @Override
    public Action getReversed() {
        return null;
    }

}
