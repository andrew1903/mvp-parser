package ua.andrew1903.mostvaluableperson.model;

public interface Player {
    /**
     * Player's nickname. Should be unique.
     */
    String getNickname();

    /**
     * Player's score.
     */
    int getScore();

    /**
     * Player's team. Must not be null.
     */
    String getTeam();

    /**
     * States whether the player is a winner.
     */
    boolean isWinner();

    /**
     * States whether the player is a winner.
     */
    void setWinner(boolean winner);

    /**
     * States whether the player is a winner.
     */
    default void setWinner(String team) {
        setWinner(getTeam().equals(team));
    }
}
