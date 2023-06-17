package ua.andrew1903.mostvaluableperson.service;

import ua.andrew1903.mostvaluableperson.model.Player;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TournamentService {
    Map<String, Player> parse(MultipartFile[] files);

    Map<String, Player> parse(MultipartFile file);
}
