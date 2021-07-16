package com.example.demo.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;

/**
 * @author lh0
 * @date 2021/6/18
 * @desc
 */
public class Server {

    public static void main(String[] args) {
        new Server().start(8088);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(6);

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server start[][][][][][]");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("client connected[][][][][]");
                executorService.execute(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        // 如果使用长连接  server端则不能简单使用ObjectInputStream 需要在read的时候多次循环等待直到read读出来的是exit
                        // 同时client端  保持长连接 没有请求则挂起等待
                        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                             OutputStream outputStream = socket.getOutputStream()) {
                            String line;
                            while ((line=bufferedReader.readLine()) != null) {
                                System.out.println("server receive client message:" + line);
                            }
                            System.out.println("client done");
                            outputStream.write(("hello"+"\n").getBytes());
                            outputStream.flush();
                        } catch (IOException e) {
                            System.err.println("client request error");
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
