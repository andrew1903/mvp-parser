package com.codetest.mostvaluableperson.model;

public interface Player extends Comparable<Player> {
    /**
     * @return Player's score
     */
    int getScore();

    default int compareTo(Player o) {
        if (!getClass().equals(o.getClass())) {
            throw new UnsupportedOperationException("Cannot compare players of different games");
        }

        return Integer.compare(getScore(), o.getScore());
    }
}
