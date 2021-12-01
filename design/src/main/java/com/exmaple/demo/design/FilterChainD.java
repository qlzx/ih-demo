package com.exmaple.demo.design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc
 */
public class FilterChainD {

    interface Filter{
        boolean doAction(Object data);
    }

    static class FilterChain {
        List<Filter> filterList = new ArrayList<>();

        void addFilter(Filter filter){
            filterList.add(filter);
        }

        void doFilter(Object data){
            for (Filter filter : filterList) {
                if (!filter.doAction(data)) {
                    break;
                }
            }
        }
    }

    static class Filter1 implements Filter {
        @Override
        public boolean doAction(Object data) {
            return data!=null;
        }
    }

    static class Filter2 implements Filter{

        @Override
        public boolean doAction(Object data) {
            return "OK".equals(data);
        }
    }

    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new Filter1());
        filterChain.addFilter(new Filter2());

        filterChain.doFilter("OK");
    }

}
