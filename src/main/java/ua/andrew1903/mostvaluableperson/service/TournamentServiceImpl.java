package ua.andrew1903.mostvaluableperson.service;

import ua.andrew1903.mostvaluableperson.exception.InternalServerException;
import ua.andrew1903.mostvaluableperson.exception.ParseException;
import ua.andrew1903.mostvaluableperson.model.Player;
import ua.andrew1903.mostvaluableperson.parser.Parser;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final Map<String, Parser> parsers;

    public TournamentServiceImpl(ApplicationContext context) {
        parsers = context.getBeansOfType(Parser.class).values().stream()
                .collect(toMap(Parser::getName, parser -> parser));
    }

    @Override
    public Map<String, Object> parse(MultipartFile[] files) {

        var players = Arrays.stream(files)
                .flatMap(file -> parse(file).stream())
                .collect(groupingBy(Player::getNickname));

        var playerScores = players.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(Player::getScore).sum()
                ));

        int mvpValue = playerScores.values().stream()
                .max(Integer::compare)
                .orElseThrow(() -> new InternalServerException("No mvp found!"));

        return Map.of(
                "mvpScore", mvpValue,
                "players", playerScores.entrySet().stream()
                        .filter(entry -> entry.getValue() == mvpValue)
                        .collect(toMap(Map.Entry::getKey, entry -> players.get(entry.getKey())))
        );
    }

    private List<Player> parse(MultipartFile file) {
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            var players = new ArrayList<Player>();
            var line = reader.readLine();
            var parser = parsers.get(line.toUpperCase());

            if (parser == null) {
                throw new InternalServerException("Parser for " + line + " not found");
            }

            var pattern = parser.getPattern();

            while ((line = reader.readLine()) != null) {
                var matcher = pattern.matcher(line);

                if (!matcher.matches()) {
                    throw new ParseException("Line " + line + " does not match pattern " + pattern.pattern());
                }

                players.add(parser.parse(matcher));
            }

            var winner = parser.determineWinner(players);

            players.forEach(player -> player.setWinner(winner));

            return players;
        } catch (IOException | NullPointerException ex) {
            throw new InternalServerException("Error reading file " + file.getOriginalFilename(), ex);
        }
    }

}
