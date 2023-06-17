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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final Map<String, Parser<?>> parsers;

    public TournamentServiceImpl(ApplicationContext context) {
        parsers = context.getBeansOfType(Parser.class).values().stream()
                .collect(HashMap::new, (map, parser) -> map.put(parser.getName().toUpperCase(), parser), (map1, map2) -> {});
    }

    @Override
    public Map<String, Player> parse(MultipartFile[] files) {
        return Arrays.stream(files)
                .flatMap(file -> parse(file).entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Player> parse(MultipartFile file) {
        try(var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
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

            return players.stream().max(Player::compareTo)
                    .map(player -> Map.of(parser.getName(), player))
                    .orElseThrow(() -> new InternalServerException("No players found in " + file.getOriginalFilename()));
        } catch (IOException ex) {
            throw new InternalServerException("Error reading file " + file.getOriginalFilename(), ex);
        }
    }

}
