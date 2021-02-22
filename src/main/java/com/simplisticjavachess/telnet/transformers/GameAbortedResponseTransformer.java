package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.telnet.Command;
import com.simplisticjavachess.telnet.CommandEnum;
import com.simplisticjavachess.telnet.ResponseTransformer;

public class GameAbortedResponseTransformer implements ResponseTransformer {

    @Override
    public boolean isApplicable(String str) {
        return str.contains("Your opponent has aborted the game");
    }

    @Override
    public Command apply(String line) {
        return new Command(CommandEnum.GAME_ABORTED, "");
    }

}
