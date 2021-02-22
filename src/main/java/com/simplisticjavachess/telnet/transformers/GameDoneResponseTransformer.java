package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.telnet.Command;
import com.simplisticjavachess.telnet.CommandEnum;
import com.simplisticjavachess.telnet.ResponseTransformer;

public class GameDoneResponseTransformer implements ResponseTransformer {

    @Override
    public boolean isApplicable(String str) {
        return str.contains("Game") &&
                (str.contains("1-0") || str.contains("0-1") || str.contains("1/2-1/2")) ;
    }

    @Override
    public Command apply(String line) {
        return new Command(CommandEnum.GAME_DONE, "");
    }

}
