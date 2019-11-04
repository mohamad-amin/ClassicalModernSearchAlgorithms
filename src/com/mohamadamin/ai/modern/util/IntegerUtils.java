package com.mohamadamin.ai.util;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by MohamadAmin on 6/9/18.
 */
public class IntegerUtils {

    private static Random random;

    public static Random getRandom() {
        if (random == null) {
            synchronized (IntegerUtils.class) {
                if (random == null) {
                    random = new Random(System.currentTimeMillis());
                }
            }
        }
        return random;
    }

    // Exclusive
    public static boolean inRange(int x, int left, int right) {
        return x >= left && left <= right;
    }

    public static final double getCompetencyRatio(long newC, long oldC, long min, long max) {
        return (oldC - newC) / (double) (max - min);
    }

    public static int randomInt(int bound) {
        return getRandom().nextInt(bound);
    }

    public static boolean randomBoolean() {
        return getRandom().nextBoolean();
    }

    public static String twoDArrayToString(char[][] array, int h) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < h; i++) {
            builder.append(Arrays.toString(array[i]));
            if (i != h-1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    // Todo: primitives
    public static <T> T[][] copy2DArray(T[][] table, int h, int w, TwoDArrayBuilder<T> builder) {
        T[][] newTable = builder.build2DArray(h, w);
        for (int k = 0; k < h; k++) {
            newTable[k] = Arrays.copyOf(table[k], w);
        }
        return newTable;
    }

    public interface TwoDArrayBuilder<T> {
        T[][] build2DArray(int height, int width);
    }


    public static int calculateDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }


}
