package org.playtox.util;

import java.util.OptionalInt;
import java.util.Random;

public class RandomUtils {

    public static int randomIntWithExclude(int min,
                                           int max,
                                           int exclude) {
        OptionalInt random = new Random().ints(min, max)
                                         .filter(num -> num != exclude)
                                         .findFirst();

        return random.orElseThrow();
    }

    public static int randomInt(int min,
                                int max) {
        return new Random().ints(min, max)
                           .findFirst()
                           .orElseThrow();
    }
}
