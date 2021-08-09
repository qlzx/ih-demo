package com.example.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lh0
 * @date 2021/8/7
 * @desc
 */

@Slf4j
public class SelectorServer {

    private volatile boolean stopped;
    public static void main(String[] args) throws IOException {

        Selector readableSelect = Selector.open();

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(8099));

            serverSocketChannel.configureBlocking(false);
        }

    }

    public void start(){

    }

    public void stop(){
        this.stopped = true;
    }

    class AcceptThread extends SelectorThread {
        private final ServerSocketChannel ss;

        public AcceptThread(ServerSocketChannel serverSocketChannel,InetSocketAddress addr) throws IOException {
            super("Accept-Thread:" + addr);
            this.ss = serverSocketChannel;
            ss.register(selector, SelectionKey.OP_ACCEPT);
        }

        @SneakyThrows
        @Override
        public void run() {
            while (!stopped && !ss.socket().isClosed()) {
                try {
                    select();
                } catch (RuntimeException e) {
                    log.warn("Ignoring unexpected runtime exception", e);
                } catch (Exception e) {
                    log.warn("Ignoring unexpected exception", e);
                } finally {
                    closeSelector();
                }
            }
        }

        private void closeSelector() {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void select() {

            try {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                ArrayList<SelectionKey> selectList = new ArrayList<>(selectionKeys);
                Collections.shuffle(selectList);

                Iterator<SelectionKey> iterator = selectList.iterator();
                while (!stopped && iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (!key.isValid()) {
                        cleanupSelectionKey(key);
                    }

                    if (key.isAcceptable()) {
                        // accept SocketChannel
                        // push to readableSelectorThread
                        if(!doAccept()){
                            //todo pauseSelector
                        }

                    }
                }
            } catch (IOException e) {
                log.warn("Ignoring IOException while selecting", e);
            }
        }

        private boolean doAccept() {
            SocketChannel sc = null;
            boolean accepted = false;
            try {
                sc = ss.accept();
                accepted = true;
                log.debug("Accepted socket connection from {}", sc.socket().getRemoteSocketAddress());

                sc.configureBlocking(false);

                //

            } catch (IOException e) {
                fastCloseSocket(sc);
            }

            return accepted;

        }

        private void fastCloseSocket(SocketChannel sc){
            if (sc != null) {
                try {
                    // Hard close immediately, discarding buffers
                    sc.socket().setSoLinger(true, 0);
                } catch (SocketException e) {
                    log.warn("Unable to set socket linger to 0, socket close may stall in CLOSE_WAIT", e);
                }
                closeSock(sc);
            }
        }

        private void cleanupSelectionKey(SelectionKey key) {
            if (key != null) {
                try {
                    key.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void closeSock(SocketChannel sock){
            if (!sock.isOpen()) {
                return;
            }

            try {
                /*
                 * The following sequence of code is stupid! You would think that
                 * only sock.close() is needed, but alas, it doesn't work that way.
                 * If you just do sock.close() there are cases where the socket
                 * doesn't actually close...
                 */
                sock.socket().shutdownOutput();
            } catch (IOException e) {
                // This is a relatively common exception that we can't avoid
                log.debug("ignoring exception during output shutdown", e);
            }
            try {
                sock.socket().shutdownInput();
            } catch (IOException e) {
                // This is a relatively common exception that we can't avoid
                log.debug("ignoring exception during input shutdown", e);
            }
            try {
                sock.socket().close();
            } catch (IOException e) {
                log.debug("ignoring exception during socket close", e);
            }
            try {
                sock.close();
            } catch (IOException e) {
                log.debug("ignoring exception during socketchannel close", e);
            }
        }
    }


    private class ReadableThread extends SelectorThread{

        private SocketChannel clientChannel ;

        public ReadableThread(SocketChannel sc) throws IOException {
            super("Read-Thread");

            this.clientChannel = sc;
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
        }

        @Override
        public void run() {
            try{
                select();
            } catch (Exception e){

            }
        }

        private void select() {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                ArrayList<SelectionKey> keysList = new ArrayList<>(selectionKeys);
                Collections.shuffle(keysList);
                Iterator<SelectionKey> iterator = keysList.iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();

                }

            }catch (IOException e){
                // log.warn();

            }

        }
    }
}
