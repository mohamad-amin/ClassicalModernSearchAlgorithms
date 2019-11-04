package com.mohamadamin.ai.optimization.problem.keyboardbuilder;

import com.mohamadamin.ai.optimization.base.problem.OptimizationAction;
import com.mohamadamin.ai.optimization.base.problem.OptimizationProblem;
import com.mohamadamin.ai.util.Environment;
import com.mohamadamin.ai.util.IntegerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mohamadamin.ai.util.IntegerUtils.randomBoolean;
import static com.mohamadamin.ai.util.IntegerUtils.randomInt;

/**
 * Created by MohamadAmin on 6/15/18.
 */
public class KeyboardBuilding implements OptimizationProblem<Keyboard, ButtonTransition> {

    private static final String[] MOST_USED = new String[] {
            "E", "T", "A", "I", "N", "O", "S", "H", "R"
    };

    private static final String[] KEEP_FAR = new String[] {
            "TH", "ER", "ON", "AN", "RE", "HE", "IN", "ED"
    };

    @Override
    public Keyboard getNewInitialState() {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        for (char i = 'A'; i <= 'Z'; i++) {
            if ((randomBoolean() && right.length() != 13) || left.length() == 13) {
                right.append(i);
            } else {
                left.append(i);
            }
        }
//        System.out.println("Left -> " + left + ", Right -> " + right);
        return new Keyboard(left.toString(), right.toString());
    }

    @Override
    public void retrieveInput() {}

    @Override
    public List<ButtonTransition> getActions(Keyboard state) {
        List<ButtonTransition> transitions = new ArrayList<>();
        for (int i = 0; i < state.getRight().length(); i++) {
            for (int j = 0; j < state.getLeft().length(); j++) {
                String left = state.getLeft().substring(0, j)
                        + state.getRight().charAt(i) + state.getLeft().substring(j+1);
                String right = state.getRight().substring(0, i)
                        + state.getLeft().charAt(j) + state.getRight().substring(i+1);
                transitions.add(new ButtonTransition(state, new Keyboard(left, right)));
            }
        }
        return transitions;
    }

    @Override
    public long getCompetency(Keyboard state) {

        int farCounter = 0;
        int mostUsedInLeft = 0;
        int mostUsedInRight = 0;

        for (String used : MOST_USED) {
            if (state.getLeft().contains(used)) {
                mostUsedInLeft++;
            } else {
                mostUsedInRight++;
            }
        }

        for (String far : KEEP_FAR) {
            String first = far.substring(0, 1);
            String second = far.substring(1);
            if (state.getRight().contains(first)) {
                if (state.getLeft().contains(second)) {
                    farCounter++;
                }
            } else if (state.getRight().contains(second)) {
                if (state.getLeft().contains(first)) {
                    farCounter++;
                }
            }
        }

        return (9 - Math.abs(mostUsedInLeft - mostUsedInRight)) + farCounter;

    }

    @Override
    public long getMinCompetency() {
        return 0;
    }

    @Override
    public long getMaxCompetency() {
        return 16;
    }

    @Override
    public ButtonTransition crossover(OptimizationAction<Keyboard> mother, OptimizationAction<Keyboard> father) {

        Keyboard motherState = mother.getDestinationState();
        Keyboard fatherState = father.getDestinationState();

        String motherString = motherState.getLeft().length() > motherState.getRight().length() ?
                motherState.getLeft() : motherState.getRight();
        int count = randomInt(motherString.length());

        List<Character> changes = new ArrayList<>();
        boolean selected[] = new boolean[motherString.length()];
        for (int i = 0; i < count; i++) {
            int victim = randomInt(motherString.length());
            if (!selected[victim]) {
                changes.add(motherString.charAt(victim));
                selected[victim] = true;
            } else {
                i--;
            }
        }

        List<Integer> fatherLeftIndexes = new ArrayList<>();
        List<Integer> fatherRightIndexes = new ArrayList<>();
        for (char c : changes) {
            int index = fatherState.getLeft().indexOf(c);
            if (index != -1) {
                fatherLeftIndexes.add(index);
                continue;
            }
            index = fatherState.getRight().indexOf(c);
            if (index != -1) {
                fatherRightIndexes.add(fatherState.getRight().indexOf(c));
            } else {
                System.out.println("WTF STATE description:");
                System.out.println("mother string -> " + motherString);
                System.out.println("changes -> " + changes);
                System.out.println("father right -> " + fatherState.getRight());
                System.out.println("father left -> " + fatherState.getLeft());
                throw new RuntimeException("WTF State 1");
            }
        }

        String newLeft = fatherState.getLeft();
        String newRight = fatherState.getRight();

        List<Integer> savedLeft = new ArrayList<>(fatherLeftIndexes);
        List<Integer> savedRight = new ArrayList<>(fatherRightIndexes);

        Collections.shuffle(changes);
        for (char c : changes) {
//            System.out.println(Environment.DIVIDER);
            if ((randomBoolean() && fatherRightIndexes.size() > 0) || fatherLeftIndexes.size() == 0) {
                int index = fatherRightIndexes.remove(0);
//                System.out.println("old right -> " + newRight);
                newRight = newRight.substring(0, index) + c + newRight.substring(index+1);
//                System.out.println("new right -> " + newRight);
            } else {
                int index = fatherLeftIndexes.remove(0);
//                System.out.println("old left -> " + newLeft);
                newLeft = newLeft.substring(0, index) + c + newLeft.substring(index+1);
//                System.out.println("new left -> " + newLeft);
            }
        }

        if (IntegerUtils.calculateDistance(newLeft, newRight) != 13) {
            System.out.println(Environment.DIVIDER);
            System.out.println("WTF STATE description:");
            System.out.println("Mother -> " + motherState);
            System.out.println("Father -> " + fatherState);
            System.out.println("mother string -> " + motherString);
            System.out.println("changes -> " + changes);
            System.out.println("father left indexes -> " + savedLeft);
            System.out.println("father right indexes -> " + savedRight);
            System.out.println("father right -> " + fatherState.getRight());
            System.out.println("father left -> " + "" + fatherState.getLeft());
            System.out.println("new right -> " + "" + newRight);
            System.out.println("new left -> " + "" + newLeft);
            throw new RuntimeException("WTF State 2");
        }

        return new ButtonTransition(fatherState, new Keyboard(newLeft, newRight));

    }

    @Override
    public ButtonTransition mutate(OptimizationAction<Keyboard> newbie, double mutationRatio) {
        String previousLeft = newbie.getDestinationState().getLeft();
        String previousRight = newbie.getDestinationState().getRight();
        String left = previousLeft, right = previousRight;
        while (mutationRatio > 0d) {
            final int l = randomInt(previousLeft.length());
            final int r = randomInt(previousRight.length());
            left = previousLeft.substring(0, l) + previousRight.charAt(r) + previousLeft.substring(l+1);
            right = previousRight.substring(0, r) + previousLeft.charAt(l) + previousRight.substring(r+1);
            mutationRatio -= 0.2d;
            previousLeft = left;
            previousRight = right;
        }
        return new ButtonTransition(newbie.getDestinationState(), new Keyboard(left, right));
    }

}
