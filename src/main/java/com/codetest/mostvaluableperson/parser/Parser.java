package com.codetest.mostvaluableperson.parser;

import com.codetest.mostvaluableperson.model.Player;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Parser<T extends Player> {
    T parse(Matcher matcher) throws IOException;
    Pattern getPattern();
}
