package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.telnet.Command;
import com.simplisticjavachess.telnet.ResponseTransformer;
import org.apache.commons.lang3.StringUtils;

import static com.simplisticjavachess.telnet.CommandEnum.SOUGHT;

public class GameSoughtResponseTransformer implements ResponseTransformer {

    @Override
    public boolean isApplicable(String str) {
        return str.contains("seeking") && str.contains("rated") &&
                (str.contains("5 0") || str.contains("10 0") || str.contains("2 12"));
    }

    @Override
    public Command apply(String line) {
        String gameCode = StringUtils.substringBetween(line, "\"", "\"");
        return new Command(SOUGHT, gameCode);
    }

}
