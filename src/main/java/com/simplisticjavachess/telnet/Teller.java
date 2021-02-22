package com.simplisticjavachess.telnet;

import com.simplisticjavachess.move.Move;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class Teller {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final DataOutputStream dataOutputStream;

    public Teller(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    private void tell(String command) {
        try {
            dataOutputStream.writeChars(command + "\n");
            System.out.println("Told server: " + command);
        } catch (IOException e) {
            logger.warning("Error encountered while performing " + command + ". Details: " + e.toString());
        }
    }

    public void commandLogin(String username, String password) {
        tell(username);
        tell(password);
    }

    public void commandSetupEnvironment() {
        tell("set silence 1"); // Quiet when playing
        tell("set style 11");  // Computer friendly output from server
    }


    public void commandDisconnect() {
        tell("bye");
    }

    public void commandMove(Move move) {
        String moveString = move.toString();
        moveString = moveString.replace("-", "").replace("=", "").
                replace("x", "");
        tell(moveString);
    }

    public void commandSeekGame() {
        tell("seek r 2 30");
    }

    public void commandMoveD2D4() {
        tell("d2d4");
    }
}
