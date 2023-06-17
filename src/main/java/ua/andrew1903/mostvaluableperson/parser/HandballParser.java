package ua.andrew1903.mostvaluableperson.parser;

import ua.andrew1903.mostvaluableperson.model.HandballPlayer;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HandballParser implements Parser<HandballPlayer> {
    @Override
    public HandballPlayer parse(Matcher matcher) {
        return HandballPlayer.builder()
                .name(matcher.group(1))
                .nickname(matcher.group(2))
                .number(Integer.parseInt(matcher.group(3)))
                .team(matcher.group(4))
                .goalsMade(Integer.parseInt(matcher.group(5)))
                .goalsReceived(Integer.parseInt(matcher.group(6)))
                .build();
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("^([^;]+);([^;]+);(\\d+);([^;]+);(\\d+);(\\d+)");
    }

    @Override
    public String getName() {
        return "HANDBALL";
    }
}
