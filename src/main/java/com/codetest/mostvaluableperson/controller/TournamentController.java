package com.codetest.mostvaluableperson.controller;

import com.codetest.mostvaluableperson.model.Player;
import com.codetest.mostvaluableperson.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tournament")
public class TournamentController {
    private final TournamentService service;

    @PostMapping("/upload-files")
    public Map<String, Player> upload(@RequestParam("files") MultipartFile[] files) {
        return service.parse(files);
    }

    @PostMapping("/upload-file")
    public Map<String, Player> upload(@RequestParam("file") MultipartFile file) {
        return service.parse(file);
    }
}
