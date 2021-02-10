package com.simplisticjavachess;

/**
 * @author Morten Kühnrich
 */

import com.simplisticjavachess.cli.ChessCLI;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Main {
    private static void setupLogging() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(Level.FINE);
        FileHandler fileHandler = new FileHandler("Logging.txt");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public static void main(String[] param) throws Exception {
        setupLogging();
        ChessCLI chessCLI = new ChessCLI();
        chessCLI.getCLI().start();
    }
}
