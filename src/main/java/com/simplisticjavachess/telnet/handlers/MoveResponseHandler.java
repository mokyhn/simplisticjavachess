package com.simplisticjavachess.telnet.handlers;

import com.simplisticjavachess.telnet.ResponseHandler;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveResponseHandler implements ResponseHandler {

    private static final Pattern PATTERN = Pattern.compile("(\\p{Upper}/\\w\\d[-|x]\\w\\d[\\w|=]*)");

    Optional<String> parseMove(String input) {
        if (input.toLowerCase().contains("o-o")) {
            return Optional.of("o-o");
        }
        if (input.toLowerCase().contains("o-o-o")) {
            return Optional.of("o-o-o");
        }
        Matcher matcher = PATTERN.matcher(input);
        if (matcher.find()) {
            input = input.substring(matcher.start(), matcher.end());
            input = input.substring(2);
            input = input.replace("-", ""). replace("=", "");
            return Optional.of(input);
        }
        return Optional.empty();
    }

    @Override
    public boolean isApplicable(String str) {
        return parseMove(str).isPresent();
    }

    @Override
    public void apply(String line) {
        System.out.println("PARSED: " + parseMove(line).get());
    }
}
