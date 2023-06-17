package com.codetest.mostvaluableperson.service;

import com.codetest.mostvaluableperson.model.Player;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TournamentService {
    Map<String, Player> parse(MultipartFile[] files);

    Map<String, Player> parse(MultipartFile file);
}
