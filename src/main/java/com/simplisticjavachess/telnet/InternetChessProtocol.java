package com.simplisticjavachess.telnet;

import com.simplisticjavachess.game.ChessGame;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.telnet.transformers.GameAbortedResponseTransformer;
import com.simplisticjavachess.telnet.transformers.GameCreatedResponseTransformer;
import com.simplisticjavachess.telnet.transformers.GameDoneResponseTransformer;
import com.simplisticjavachess.telnet.transformers.GameSoughtResponseTransformer;
import com.simplisticjavachess.telnet.transformers.MoveResponseTransformer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class InternetChessProtocol {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Teller teller;

    private ConcurrentLinkedQueue<Command> commands;

    private String serverUrl;
    private Integer portNumber;

    Socket socket;
    DataInputStream fromServer;
    DataOutputStream toServer;

    Listener listener;

    ChessGame chessGame;

    public InternetChessProtocol(String serverUrl, Integer portNumber) {
        this.serverUrl = serverUrl;
        this.portNumber = portNumber;
        this.chessGame = new ChessGame();
        this.commands = new ConcurrentLinkedQueue<>();
    }

    public void connect(String username, String password) {
        if (socket == null) {
            try {
                socket = new Socket("freechess.org", portNumber);
                fromServer = new DataInputStream(socket.getInputStream());
                toServer = new DataOutputStream(socket.getOutputStream());
                teller = new Teller(toServer);
                listener =
                        new Listener(fromServer,
                                commands,
                                new MoveResponseTransformer(),
                                new GameCreatedResponseTransformer(username),
                                new GameAbortedResponseTransformer(),
                                new GameDoneResponseTransformer(),
                                new GameSoughtResponseTransformer());
                new Thread(listener).start();
                teller.commandLogin(username, password);
            } catch (IOException e) {
                logger.warning("Could not make net connection to " + serverUrl);
            }
        } else {
            logger.warning("Could not make net connection to " + serverUrl);
        }
    }


    public void run(String username, String password) throws InterruptedException {
        connect(username, password);
        Thread.sleep(4000);
        teller.commandSetupEnvironment();
        Thread.sleep(1000);
        teller.commandSeekGame();
        int gamesToGo = 0;
        while (true) {
            if (commands.isEmpty()) {
                continue;
            }
            Command nextCommand = commands.poll();
            switch (nextCommand.getCommand()) {
                case GAME_ABORTED:
                    // Indented fallthrough
                case GAME_DONE:
                    if (gamesToGo > 0) {
                        chessGame = new ChessGame();
                        commands.clear();
                        teller.commandSeekGame();
                        gamesToGo--;
                    } else {
                        listener.doStop();
                        teller.commandDisconnect();
                        Thread.sleep(2000);
                        return;
                    }
                    break;
                case MOVE:
                    try {
                        chessGame.move(nextCommand.getArgument());
                    } catch (IllegalArgumentException e) {}

                    if (chessGame.isTheComputerToMove()) {
                        Optional<Move> move = chessGame.search();
                        if (move.isPresent()) {
                            teller.commandMove(move.get());
                        }
                    }
                    break;
                case NEW_GAME:
                    chessGame = new ChessGame();
                    commands.clear();
                    if ("AS_WHITE".equals(nextCommand.getArgument())) {
                        chessGame.setComputerColor(Color.WHITE);
                        teller.commandMoveD2D4();
                    } else {
                        chessGame.setComputerColor(Color.BLACK);
                    }
                    break;
                case SOUGHT:
                    // No reaction so far but we may accept seeks in the future
                    break;
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        String username = args[0];
        String password = args[1];
        InternetChessProtocol protocol = new InternetChessProtocol("freechess.org", 5000);
        protocol.run(username, password);
    }
}
