package com.codetest.mostvaluableperson.controller;

import com.codetest.mostvaluableperson.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class TournamentController {

    private final TournamentService service;

    @PostMapping("/files")
    public void upload(@RequestParam("files")MultipartFile[] files) {
        var parsed = service.parse(files);
        System.out.println(parsed);
    }
}
