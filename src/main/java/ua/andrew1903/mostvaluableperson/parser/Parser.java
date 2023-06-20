package ua.andrew1903.mostvaluableperson.parser;

import ua.andrew1903.mostvaluableperson.model.Player;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public interface Parser {

    /**
     * Parses a line matched against the pattern provided by {@link #getPattern()}. The matcher is guaranteed to match.
     * @param matcher Matcher to parse. Values are usually obtained via {@link Matcher#group(int)} method.
     * @return New player instance
     */
    Player parse(Matcher matcher);

    /**
     * @return Compiled pattern
     */
    Pattern getPattern();

    /**
     * Name of game type.
     * Used to compare the first line of a file to match the parsing method.
     * Case-insensitive.
     */
    String getName();

    /**
     * Groups a collection of players by team and total score and returns the team  with the highest score. Null if not found.
     * @param players Players parsed game.
     * @return Winner`s name
     */
    default String determineWinner(Collection<Player> players) {
        var teamScores = players.stream()
                .collect(groupingBy(Player::getTeam, summingInt(Player::getScore)))
                .entrySet();

        return teamScores.stream().map(Map.Entry::getValue).distinct().count() == 1
                ? null
                : teamScores.stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

}
