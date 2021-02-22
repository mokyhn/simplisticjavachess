package com.simplisticjavachess.telnet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Listener implements Runnable {
    private final ConcurrentLinkedQueue<Command> commandsQueue;
    private final BufferedReader bufferedReader;
    private final List<ResponseTransformer> responseTransformerList;

    private boolean running;

    public Listener(DataInputStream dataInputStream, ConcurrentLinkedQueue<Command> commandsQueue, ResponseTransformer... responseTransformers) {
        running = false;
        bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
        this.commandsQueue = commandsQueue;
        responseTransformerList = new ArrayList<>();
        responseTransformerList.addAll(Arrays.asList(responseTransformers));
    }

    public void run() {
        running = true;

        String line = "";

        try {
            while ((running && (line = bufferedReader.readLine()) != null)) {
                line = line.substring(0, Integer.min(140, line.length()));
                System.out.println(line);
                for (ResponseTransformer handler : responseTransformerList) {
                    if (handler.isApplicable(line)) {
                        commandsQueue.add(handler.apply(line));
                    }
                }
            }
        } catch (IOException e) { }
    }

    public void doStop() {
        running = false;
    }
}
