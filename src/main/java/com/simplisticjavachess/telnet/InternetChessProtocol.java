package com.simplisticjavachess.telnet;

import com.simplisticjavachess.telnet.handlers.MoveResponseHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class InternetChessProtocol {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    private String serverUrl;
    private Integer portNumber;

    Socket socket;
    DataInputStream fromServer;
    DataOutputStream toServer;

    ListenerRunner listenerRunner;

    public InternetChessProtocol(String serverUrl, Integer portNumber) {
        this.serverUrl = serverUrl;
        this.portNumber = portNumber;
    }

    private void tellServer(String command) {
        try {
            toServer.writeChars(command+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(String username, String password) {
        if (socket == null) {
            try {
                socket = new Socket("freechess.org", portNumber);
                fromServer = new DataInputStream(socket.getInputStream());
                toServer = new DataOutputStream(socket.getOutputStream());
                listenerRunner = new ListenerRunner(fromServer, new MoveResponseHandler());
                new Thread(listenerRunner).start();
                tellServer(username);
                tellServer(password);
            } catch (IOException e) {
                logger.warning("Could not make net connection to " + serverUrl);
            }
        } else {
            logger.warning("Could not make net connection to " + serverUrl);;
        }
    }

    public void setupenvironment() {
        tellServer("set silence 1"); // Quiet when playing
        tellServer("set style 11");  // Computer friendly output from server
    }

    public void disconnect() {
        if (socket == null) {
            logger.warning("Connection lost while trying to disconnect...");
        }
        else {
            try {
                tellServer("bye");
                Thread.sleep(1000);
                listenerRunner.doStop();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InternetChessProtocol protocol = new InternetChessProtocol("freechess.org", 5000);
        protocol.connect("sjcmku\n", "\n");
        Thread.sleep(4000);
        protocol.setupenvironment();
        Thread.sleep(1000);
//        protocol.tellServer("seek u 2 12");
        protocol.disconnect();
    }
}
