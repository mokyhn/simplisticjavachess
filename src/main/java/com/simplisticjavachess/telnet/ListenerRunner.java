package com.simplisticjavachess.telnet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenerRunner implements Runnable {
    private boolean running;

    private final BufferedReader bufferedReader;

    private final List<ResponseHandler> responseHandlerList;

    public ListenerRunner(DataInputStream dataInputStream, ResponseHandler... responseHandlers) {
        running = false;
        bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
        responseHandlerList = new ArrayList<>();
        responseHandlerList.addAll(Arrays.asList(responseHandlers));
    }

    public void run() {
        running = true;

        String line = "";

        try {
            while ((running && (line = bufferedReader.readLine()) != null)) {
                line = line.substring(0, Integer.min(140, line.length()));
                for (ResponseHandler handler : responseHandlerList) {
                    if (handler.isApplicable(line)) {
                        handler.apply(line);
                    }
                }

                System.out.println(line);
            }
        } catch (IOException e) { }
    }

    public void doStop() {
        running = false;
    }
}
