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
    public void successfulTest() throws IOException {
        var files = new MultipartFile[]{file("/basketball.csv"), file("/handball.csv")};
        assertThat(service.parse(files)).isEqualTo(Map.entry("nick3", 54));
    }

    @Test
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
    public void noMVPFound() throws IOException {
        var files = new MultipartFile[]{file("/without_mvp.csv")};
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
    public void emptyParserTest() throws IOException {
        var files = new MultipartFile[]{file("/unknown.csv")};
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("Parser for  not found");
    }

    @Test
    public void emptyFileTest() throws IOException {
        var files = new MultipartFile[]{new MockMultipartFile(" ", new byte[0])};
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessageStartingWith("Error reading file");
    }

    @Test
    public void parserNotFoundTest() throws IOException {
        var files = new MultipartFile[]{file("/volleyball.csv")};
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(InternalServerException.class)
                .hasMessageStartingWith("Parser for")
                .hasMessageEndingWith("not found");
    }

    @Test
    public void invalidDataTest() throws IOException {
        var files = new MultipartFile[]{file("/basketball_invalid.csv")};
        assertThatThrownBy(() -> service.parse(files))
                .isInstanceOf(ParseException.class)
                .hasMessageContaining("does not match pattern");
    }

    private MultipartFile file(String name) throws IOException {
        var in = getClass().getResourceAsStream(name);

        if (in == null) {
            throw new IOException(String.format("File not found at path \"%s\"", name));
        }

        return new MockMultipartFile(name, in.readAllBytes());
    }

}
