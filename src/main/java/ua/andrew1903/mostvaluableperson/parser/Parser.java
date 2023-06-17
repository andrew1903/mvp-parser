package ua.andrew1903.mostvaluableperson.parser;

import ua.andrew1903.mostvaluableperson.model.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Parser<T extends Player> {
    /**
     * Parses a line matched against the pattern provided by {@link #getPattern()}. The matcher is guaranteed to match.
     * @param matcher Matcher to parse. Values are usually obtained via {@link Matcher#group(int)} method.
     * @return New player instance
     */
    T parse(Matcher matcher);

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
}
