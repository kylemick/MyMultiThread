package com.ck;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by ٩ on 2015/4/27.
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private InputStream inputStream;
    private final int BUFSZ = 1024;
    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
    }

    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
        finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {

        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = inputStream.read(buf);
                if (count < 0) {
                    break;
                }
                else if (count > 0) {
                    System.out.println(new String(buf));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
