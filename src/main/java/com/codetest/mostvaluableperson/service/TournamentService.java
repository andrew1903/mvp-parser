package com.codetest.mostvaluableperson.service;

import com.codetest.mostvaluableperson.parser.Parser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TournamentService {

    List<? extends Map<String, Object>> parse(MultipartFile[] files);
    Map<String, Object> parse(MultipartFile file);
}
