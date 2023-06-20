package ua.andrew1903.mostvaluableperson.controller;

import ua.andrew1903.mostvaluableperson.service.TournamentService;
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

    /**
     * Parses given files and returns MVPs in following format: <br>
     * <pre>
     * {
     *     "mvpScore": 0,
     *     "players": {
     *         "nickname": [...]
     *     }
     * }
     * </pre>
     * mupScore is the sum of all MVPs' scores. <br>
     * players is a map of nickname to player records. With key being player's nickname, and value being the list of all parsed records from all provided files.
     * @param files - array of MultipartFile. Could be empty (will return an error).
     * @return An object with mvpScore and players fields. players is a map of nickname to player records.
     */
    @PostMapping("/upload-files")
    public Map<String, Object> upload(@RequestParam("files") MultipartFile[] files) {
        return service.parse(files);
    }
}
