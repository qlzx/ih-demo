package com.example.demo.io.bio.im;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lh0
 * @date 2021/6/19
 * @desc
 */
@Slf4j
public class ImServer {
    ExecutorService executorService = Executors.newFixedThreadPool(6);

    private Map<String, Socket> socketMap = new ConcurrentHashMap<>();


    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("ImServer start");
            while (true) {
                Socket socket = serverSocket.accept();
                log.info("client connected :{}",socket.getRemoteSocketAddress().toString());
                executorService.execute(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        doMessage(socket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doMessage(Socket socket) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStreamReader = new ObjectInputStream(
            socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(
                 socket.getOutputStream())) {
            Message message;
            do {
                message = (Message) inputStreamReader.readObject();
                log.info("server receive client message:{}", message);
                sendTo(message);
            } while ("stop".equals(message.getContent()));
        } catch (IOException | ClassNotFoundException e) {
            log.error("server do message fail", e);
            throw e;
        }
    }

    private void sendTo(Message message) {
    }

    public void stop(){
        socketMap.forEach((k,v)->{
            try {
                v.close();
            } catch (IOException e) {
                log.debug("socket close fail.",e);
                // ignore
            }
        });
    }
}
