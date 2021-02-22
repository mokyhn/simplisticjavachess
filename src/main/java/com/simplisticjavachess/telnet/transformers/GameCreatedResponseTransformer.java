package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.telnet.Command;
import com.simplisticjavachess.telnet.CommandEnum;
import com.simplisticjavachess.telnet.ResponseTransformer;

public class GameCreatedResponseTransformer implements ResponseTransformer {
    private final String myUserName;

    public GameCreatedResponseTransformer(String myUserName) {
        this.myUserName = myUserName;
    }

    private boolean amIWhite(String str) {
        String[] strings = str.split(" ");
        return myUserName.equals(strings[1]);
    }

    @Override
    public boolean isApplicable(String str) {
        return str.contains("Creating: ") && str.contains("rated");
    }

    @Override
    public Command apply(String line) {
        return (amIWhite(line) ? new Command(CommandEnum.NEW_GAME, "AS_WHITE") :
                new Command(CommandEnum.NEW_GAME, "AS_BLACK"));
    }

}
