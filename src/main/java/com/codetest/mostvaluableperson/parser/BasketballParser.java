package com.codetest.mostvaluableperson.parser;

import com.codetest.mostvaluableperson.model.BasketballPlayer;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BasketballParser implements Parser<BasketballPlayer> {
    @Override
    public BasketballPlayer parse(Matcher matcher) {
        return BasketballPlayer.builder()
                .name(matcher.group(1))
                .nickname(matcher.group(2))
                .number(Integer.parseInt(matcher.group(3)))
                .team(matcher.group(4))
                .points(Integer.parseInt(matcher.group(5)))
                .rebounds(Integer.parseInt(matcher.group(6)))
                .assists(Integer.parseInt(matcher.group(7)))
                .build();
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("^([^;]+);([^;]+);(\\d+);([^;]+);(\\d+);(\\d+);(\\d+)");
    }

    @Override
    public String getName() {
        return "BASKETBALL";
    }
}
