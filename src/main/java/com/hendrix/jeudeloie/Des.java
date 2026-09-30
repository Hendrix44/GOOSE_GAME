package com.hendrix.jeudeloie;

import java.util.Random;

/**
 * Deux dés à six faces.
 */
public class Des {

    private final Random random;

    public Des() {
        this(new Random());
    }

    /** Permet d'injecter un Random (utile pour des tests reproductibles). */
    public Des(Random random) {
        this.random = random;
    }

    public int[] lancer() {
        return new int[]{random.nextInt(6) + 1, random.nextInt(6) + 1};
    }
}
