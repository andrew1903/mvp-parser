package com.codetest.mostvaluableperson.service;

import com.codetest.mostvaluableperson.model.Player;
import com.codetest.mostvaluableperson.parser.BasketballParser;
import com.codetest.mostvaluableperson.parser.HandballParser;
import com.codetest.mostvaluableperson.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final Map<String, Parser<?>> parsers = Map.of(
            "HANDBALL", new HandballParser(),
            "BASKETBALL", new BasketballParser()
    );

    @Override
    public List<Map<String, Object>> parse(MultipartFile[] files) {
        return Arrays.stream(files).map(this::parse).toList();
    }

    @Override
    public Map<String, Object> parse(MultipartFile file) {

        var players = new ArrayList<Player<?>>();
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            var line = reader.readLine();
            var parser = parsers.get(line.toUpperCase());

            while ((line = reader.readLine()) != null) {
                var matcher = parser.getPattern().matcher(line);
                if (!matcher.matches())
                    throw new RuntimeException();
                var player = parser.parse(matcher);
                players.add(player);
            }

            return players.stream().sorted().findFirst().orElseThrow().toMap();
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
