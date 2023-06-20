package ua.andrew1903.mostvaluableperson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ua.andrew1903.mostvaluableperson.exception.InternalServerException;
import ua.andrew1903.mostvaluableperson.exception.ParseException;
import ua.andrew1903.mostvaluableperson.service.TournamentService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TournamentServiceTest {

    @Autowired
    private TournamentService service;

    @Test
    public void successfulTest() {
        var files = getFiles("basketball.csv", "handball.csv");
        assertThat(service.parse(files)).isEqualTo(Map.entry("nick3", 54));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void successfulBatchTest() throws URISyntaxException {
        var files = Arrays.stream(new File(getClass().getResource("/batch").toURI().getPath()).listFiles())
                .map(file -> {
                    try {
                        return new MockMultipartFile(file.getName(), Files.readAllBytes(file.toPath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(MultipartFile[]::new);
        assertThat(service.parse(files)).isEqualTo(Map.entry("nick3", 324));
    }

    @Test
    public void noMVPFound() {
        var files = getFiles("without_mvp.csv");
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("No mvp found!");
    }

    @Test
    public void nullPointerTest() {
        assertThatThrownBy(() -> service.parse(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Cannot read the array length because \"array\" is null");
    }

    @Test
    public void emptyParserTest() {
        var files = getFiles("unknown.csv");
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("Parser for  not found");
    }

    @Test
    public void emptyFileTest() {
        var files = new MultipartFile[]{new MockMultipartFile(" ", new byte[0])};
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessageStartingWith("Error reading file");
    }

    @Test
    public void parserNotFoundTest() {
        var files = getFiles("volleyball.csv");
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessageStartingWith("Parser for")
                .hasMessageEndingWith("not found");
    }

    @Test
    public void invalidDataTest() {
        var files = getFiles("basketball_invalid.csv");
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(ParseException.class)
                .hasMessageContaining("does not match pattern");
    }

    private MultipartFile[] getFiles(String ...names) {
        return Arrays.stream(names).map(name -> {
            try {
                return new MockMultipartFile(name, getClass().getResourceAsStream("/" + name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toArray(MultipartFile[]::new);
    }
}
