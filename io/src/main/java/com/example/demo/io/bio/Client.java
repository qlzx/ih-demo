package com.example.demo.io.bio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author lh0
 * @date 2021/6/18
 * @desc
 */
public class Client {
    public static void main(String[] args) {
        System.out.println(
            "client receive:" +
                new Client().send("client write", "127.0.0.1", 8000));
    }

    public String send(String message, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            try (OutputStream objectOutputStream = socket.getOutputStream()) {
                int i = 0;
                while (i < 10) {
                    objectOutputStream.write((message + "\n").getBytes());
                    objectOutputStream.flush();
                    i++;
                    Thread.sleep(1000);
                }
                objectOutputStream.write("exit\n".getBytes());
                objectOutputStream.flush();

                System.out.println("client write done");
                //try (InputStreamReader bufferedReader = new InputStreamReader(
                //    socket.getInputStream())) {
                //    StringBuilder result = new StringBuilder();
                //    char[] lineBuffer = new char[1024];
                //
                //    while (bufferedReader.read(lineBuffer) != -1) {
                //        result.append(new String(lineBuffer));
                //    }
                //    return result.toString();
                //}
                return null;

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
