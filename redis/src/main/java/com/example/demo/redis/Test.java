package com.example.demo.redis;

import java.util.HashMap;

/**
 * @author lh0
 * @date 2021/8/5
 * @desc
 */
public class Test {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(hash(obj));

        HashMap hashMap = new HashMap(4);

        hashMap.put(new Con("a"), "a");
        hashMap.put(new Con("b"), "b");
        hashMap.put(new Con("c"), "c");
        hashMap.put(new Con("d"), "d");
        hashMap.put(new Con("e"), "e");

        System.out.println(hashMap);

    }


    public static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static class Con{

        private final String name;

        public Con(String name) {this.name = name;}

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return "Con{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
