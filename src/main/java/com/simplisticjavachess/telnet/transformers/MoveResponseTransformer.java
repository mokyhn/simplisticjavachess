package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.telnet.Command;
import com.simplisticjavachess.telnet.CommandEnum;
import com.simplisticjavachess.telnet.ResponseTransformer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveResponseTransformer implements ResponseTransformer {
    private static final Pattern PATTERN = Pattern.compile("(\\p{Upper}/\\w\\d[-|x]\\w\\d[\\w|=]*)");

    Optional<String> parseMove(String input) {
        if (input.contains("none")) {
            return Optional.empty();
        }
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
            input = input.replace("-", ""). replace("=", "").replace("x", "");
            return Optional.of(input);
        }
        return Optional.empty();
    }

    @Override
    public boolean isApplicable(String str) {
        return parseMove(str).isPresent();
    }

    @Override
    public Command apply(String line) {
        String theMove = parseMove(line).get();
        return new Command(CommandEnum.MOVE, theMove);
    }
}
