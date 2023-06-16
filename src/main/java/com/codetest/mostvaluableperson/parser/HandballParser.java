package com.codetest.mostvaluableperson.parser;

import com.codetest.mostvaluableperson.model.HandballPlayer;
import com.codetest.mostvaluableperson.model.Player;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
