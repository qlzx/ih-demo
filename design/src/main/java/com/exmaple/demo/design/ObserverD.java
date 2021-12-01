package com.exmaple.demo.design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc
 */
public class ObserverD {

    interface Listener {
        void update(Object date);
    }

    static class Publisher {
        List<Listener> listeners = new ArrayList<>();

        void subscribe(Listener listener) {
            listeners.add(listener);
        }

        void remove(Listener listener) {
            listeners.remove(listener);
        }

        void update(Object data) {
            for (Listener listener : listeners) {
                listener.update(data);
            }
        }
    }

    static class Foo implements Listener {

        @Override
        public void update(Object date) {
            System.out.println("Foo update data:" + date);
        }
    }

    static class Bar implements Listener {

        @Override
        public void update(Object date) {
            System.out.println("Bar update data:" + date);
        }
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        Bar bar = new Bar();

        Publisher publisher = new Publisher();
        publisher.subscribe(foo);
        publisher.subscribe(bar);

        publisher.update("test");
    }
}
